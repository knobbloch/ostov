package com.knobblochsapplication.app.appcomponents.utility

import android.content.Context
import com.google.gson.GsonBuilder
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AppStorage(val context: Context) {
    var goals: MutableList<Node> = mutableListOf()

    fun addGoal(
        name: String?,
        deadline: String?,
        priority: Int,
        description: String?
    ) {
        val goal = Node(
            UUID.randomUUID().toString(),
            name = name,
            deadline = deadline,
            priority = priority,
            isDone = false,
            description = description,
            tasks = mutableListOf()
        )
        goals.add(goal)
        saveToFile(goal)
    }

    fun getGoalByUid(uid: String): Node? {
        var goal = goals.find {
            it.uid == uid
        }
        if (goal != null) {
            return goal
        }
        return null
    }

    fun loadAll(): AppStorage {
        val files = context.filesDir.listFiles()
        if (files == null) {
            println("!!!error")
            return this
        }
        for (file in files) {
            println("!!!" + files.size)
            if (!file.name.endsWith(".json")) {
                continue
            }
            var goal = loadByUid(file)
            if (goal !== null) {
                goals.add(goal)
            }
        }
        return this
    }

    fun addTask(
        goalUid: String,
        name: String,
        deadline: String?,
        priority: Int,
        description: String?
    ) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        val task = Node(
            UUID.randomUUID().toString(),
            name = name,
            deadline = deadline,
            priority = priority,
            isDone = false,
            description = description,
            tasks = mutableListOf()
        )
        goal.tasks.add(task)
        goal.separete()
        saveToFile(goal)
    }

    fun changeTask(
        goalUid: String,
        taskUid: String,
        name: String,
        deadline: String?,
        isDone: Boolean,
        priority: Int,
        description: String?
    ) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        for (task in goal.tasks) {
            if (task.uid != taskUid) {
                continue
            }
            task.name = name
            task.description = description
            task.deadline = deadline
            task.isDone = isDone
            task.priority = priority
            saveToFile(goal)
        }
    }

    private fun loadByUid(uid: String): Node? {
        val prettyGson = GsonBuilder()
            .setPrettyPrinting()
            .create()
        val file = File(context.filesDir, getFilename(uid))
        try {
            val bytes = Files.readAllBytes(file.toPath())
            return prettyGson.fromJson(String(bytes), Node::class.java)
        } catch (e: FileNotFoundException) {
            return null
        }
    }

    private fun loadByUid(file: File): Node? {
        val prettyGson = GsonBuilder()
            .setPrettyPrinting()
            .create()
        try {
            val bytes = Files.readAllBytes(file.toPath())
            return prettyGson.fromJson(String(bytes), Node::class.java)
        } catch (e: FileNotFoundException) {
            return null
        }
    }

    fun remove(uid: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        deleteFile(uid)
        goals.remove(goal)
    }

    fun filter(uid: String): MutableList<Node> {
        return goals.filterNot {
            it.uid == uid
        }.toMutableList()
    }

    fun union(goalUid: String, taskUid: String) {
        var goal = getGoalByUid(goalUid)
        val task = getGoalByUid(taskUid)
        if (goal == null || task == null) {
            return
        }
        //todo порядок или сортировка
        goal.tasks.add(task)
        saveToFile(goal)

        deleteFile(task.uid)
        goal.separete()
        goals.remove(task)
    }

    private fun getFilename(uid: String): String {
        return "app_${uid}.json"
    }

    private fun saveToFile(goal: Node) {
        val prettyGson = GsonBuilder()
            .setPrettyPrinting()
            .create()
        val jsonContent =
            prettyGson.toJson(goal)

        val file = File(context.filesDir, getFilename(goal.uid))
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(jsonContent)
        bufferedWriter.close()
    }

    private fun deleteFile(uid: String) {
        val file = File(context.filesDir, getFilename(uid))
        if (file.exists()) {
            file.delete()
        }
    }

    private fun give_percent(goal: Node): Int {
        //Проверяем есть ли подзадачи у объекта
        if(goal.tasks.size!=0){
            //Рассчитываем сколько процентов даётся за выполнение 1 задания

            var one_task_per=100/goal.tasks.size
            var count_completed = 0

            //Считаем сколько заданий выполнено
            for(i in goal.tasks){
                if(i.isDone){
                    count_completed+=1
                }
            }

            //Возвращаем получившееся число
            if(count_completed==goal.tasks.size){
                return 100
            }else{
                return one_task_per*count_completed
            }

        }
        else{
            if(goal.isDone){
                return 100;
            }else{
                return 0;
            }
        }
    }

    fun sort_by_rang(goalUid: String) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        goal.tasks.sortBy{it.priority}
        for (t in goal.tasks){
            sort_by_rang(t.uid)
        }
    }

    fun sort_by_complete(goalUid: String) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        goal.tasks.sortBy{it.isDone}
        for (t in goal.tasks){
            sort_by_complete(t.uid)
        }
    }

    fun sort_by_deadline(goalUid: String) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a")
        goal.tasks.sortBy{ LocalDateTime.parse(it.deadline, formatter)}
        for (t in goal.tasks){
            sort_by_deadline(t.uid)
        }
    }
}

////Дальше будет несколько компараторов для сортировки
//
//internal class by_rang<T : Comparable<Node>> : Comparator<Node> {
//    override fun compare(x: Node, y: Node): Int {
//        return (x.priority).compareTo(y.priority)
//    }
//}
//
//internal class by_complete<T : Comparable<Node>> : Comparator<Node> {
//    override fun compare(x: Node, y: Node): Int {
//        return (x.isDone).compareTo(y.isDone)
//    }
//}
//
//internal class by_deadline<T : Comparable<Node>> : Comparator<Node> {
//    override fun compare(x: Node, y: Node): Int {
//        //Пример строки,которая должна быть во времени "Jan 01 2017, 07:34:27 pm"
//        val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a")
//        val date_x = LocalDateTime.parse(x.deadline, formatter)
//        val date_y = LocalDateTime.parse(y.deadline, formatter)
//        return (date_x).compareTo(date_y)
//    }
//}
//
////Конец компараторов