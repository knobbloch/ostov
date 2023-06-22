package com.knobblochsapplication.app.appcomponents.utility

import android.content.Context
import com.google.gson.GsonBuilder
import com.knobblochsapplication.app.appcomponents.utility.Docx.DocxFile
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.nio.file.Files
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AppStorage(val context: Context) {
    var goals: MutableList<Node> = mutableListOf()

    fun addGoal(
        name: String?,
        deadline: String?,
        priority: Int,
        description: String?,
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

    fun deleteGoal(uid: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        deleteFile(uid)
        goals.remove(goal)
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

    fun duplicateGoal(goalUid: String, taskUid: String) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        val task = goal.getTaskByUid(taskUid)
        if (task == null) {
            return
        }
        var newGoal = task.duplicate()
        goals.add(newGoal)
        newGoal.separate()
        saveToFile(newGoal)
    }

    fun createGoalFromTask(goalUid: String, taskUid: String) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        val task = goal.getTaskByUid(taskUid)
        if (task == null) {
            return
        }
        goal.deleteTask(taskUid)
        goals.add(task)
        goal.separate()
        task.separate()
        saveToFile(task)
        saveToFile(goal)
    }

    fun createGoalFromTaskWithoutChild(goalUid: String, taskUid: String) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        val task = goal.getTaskByUid(taskUid)
        if (task == null) {
            return
        }
        goal.deleteTaskTransferChild(taskUid)
        goals.add(task)
        task.tasks.clear()
        goal.separate()
        task.separate()
        saveToFile(task)
        saveToFile(goal)
    }

    fun addTask(
        goalUid: String,
        taskUid: String?,
        name: String,
        deadline: String?,
        priority: Int,
        description: String?,
    ) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        if (taskUid == null) {
            val newTask = Node(
                UUID.randomUUID().toString(),
                name = name,
                deadline = deadline,
                priority = priority,
                isDone = false,
                description = description,
                tasks = mutableListOf()
            )
            goal.tasks.add(newTask)
            goal.separate()
            saveToFile(goal)
            return
        }
        val parentTask = goal.getTaskByUid(taskUid)
        if (parentTask == null) {
            return
        }
        val newTask = Node(
            UUID.randomUUID().toString(),
            name = name,
            deadline = deadline,
            priority = priority,
            isDone = false,
            description = description,
            tasks = mutableListOf()
        )
        parentTask.tasks.add(newTask)
        goal.separate()
        saveToFile(goal)
    }

    fun deleteTask(goalUid: String, taskUid: String) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        goal.deleteTask(taskUid)
        goal.separate()
        saveToFile(goal)
    }

    fun deleteTaskTransferChild(goalUid: String, taskUid: String) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        goal.deleteTaskTransferChild(taskUid)
        goal.separate()
        saveToFile(goal)
    }

    fun changeTask(
        goalUid: String,
        taskUid: String,
        name: String,
        deadline: String?,
        isDone: Boolean,
        priority: Int,
        description: String?,
    ) {
        val goal = getGoalByUid(goalUid)
        if (goal == null) {
            return
        }
        val task = goal.getTaskByUid(taskUid)
        if (task == null) {
            return
        }
        task.name = name
        task.description = description
        task.deadline = deadline
        task.isDone = isDone
        task.priority = priority
        saveToFile(goal)
    }

    fun loadByUid(uid: String): Node? {
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
        goal.separate()
        goals.remove(task)
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
                goal.separate()
            }
        }
        return this
    }

    private fun getFilename(uid: String): String {
        return "app_${uid}.json"
    }

    fun saveToFile(goal: Node) {
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

    fun downloadDocxFile(uid: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        DocxFile.make_docx(goal)
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

    private fun sort_by_rang_ins(need_to_sort: Node): Node {
        if(need_to_sort.tasks.size!=0){
            need_to_sort.tasks.sortBy{it.priority}
            for (t in 0..need_to_sort.tasks.size-1){
                var changed=sort_by_rang_ins(need_to_sort.tasks.get(t))
                need_to_sort.tasks.set(t,changed)
            }
        }
        return need_to_sort
    }

    fun sort_by_rang(uid:String){
        var test= loadByUid(uid)
            ?.let { it1 -> sort_by_rang_ins(it1) }
        if (test != null) {
            test.separate()
            saveToFile(test)
        }
    }

    private fun sort_by_complete_ins(need_to_sort: Node): Node {
        if(need_to_sort.tasks.size!=0){
            need_to_sort.tasks.sortBy{it.isDone}
            for (t in 0..need_to_sort.tasks.size-1){
                var changed=sort_by_complete_ins(need_to_sort.tasks.get(t))
                need_to_sort.tasks.set(t,changed)
            }
        }
        return need_to_sort
    }

    fun sort_by_complete(uid:String){
        var test= loadByUid(uid)
            ?.let { it1 -> sort_by_complete_ins(it1) }
        if (test != null) {
            test.separate()
            saveToFile(test)
        }
    }

    private fun sort_by_deadline_ins(need_to_sort: Node): Node {
        if(need_to_sort.tasks.size!=0){
            need_to_sort.tasks.sortWith(by_deadline())
            for (t in 0..need_to_sort.tasks.size-1){
                var changed=sort_by_deadline_ins(need_to_sort.tasks.get(t))
                need_to_sort.tasks.set(t,changed)
            }
        }
        return need_to_sort
    }

    fun sort_by_deadline(uid:String){
        var test= loadByUid(uid)
            ?.let { it1 -> sort_by_deadline_ins(it1) }
        if (test != null) {
            test.separate()
            saveToFile(test)
        }
    }
}

////Компараторор для сортировки даты
internal class by_deadline: Comparator<Node> {
    override fun compare(x: Node, y: Node): Int {
        //Пример строки,которая должна быть во времени "Jan 01 2017, 07:34:27 pm"
        if(x.deadline!="" && y.deadline!="") {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val date_x = LocalDate.parse(x.deadline, formatter)
            val date_y = LocalDate.parse(y.deadline, formatter)
            return (date_x).compareTo(date_y)
        }
        if(x.deadline==y.deadline){
            return 0
        }
        if (x.deadline=="" && y.deadline!=""){
            return -1
        }else{
            return 1
        }
    }
}