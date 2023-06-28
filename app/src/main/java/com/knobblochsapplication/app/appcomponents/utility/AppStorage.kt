package com.knobblochsapplication.app.appcomponents.utility

import android.content.Context
import com.google.gson.GsonBuilder
import com.knobblochsapplication.app.appcomponents.utility.Docx.DocxFile
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.nio.file.Files
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

    fun changeGoal(
        goalUid: String,
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
        goal.name = name
        goal.description = description
        goal.deadline = deadline
        goal.isDone = isDone
        goal.priority = priority
        saveToFile(goal)
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
            return this
        }
        for (file in files) {
            if (!file.name.endsWith(".json")) {
                continue
            }
            var goal = loadByUid(file)
            if (goal !== null) {
                goals.add(goal)
                goal.separate()
            }
        }
        sortByIsDone()
        return this
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

    fun downloadDocxFile(uid: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        DocxFile.make_docx(goal)
    }

    fun sortByPriority(uid: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        goal.sortByPriority()
    }

    fun sortByDeadline(uid: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        goal.sortByDeadline()
    }

    fun sortByCompletion(uid: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        goal.sortByCompletion()
    }

    fun sortByPriorityDeadline(uid: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        goal.sortByPriorityDeadline()
    }

    fun getDeadlineToday(): MutableList<Node> {
        var list = mutableListOf<Node>()
        for (item in goals) {
            var returned = item.getTaskDeadlineToday()
            if (returned.size != 0) {
                list.addAll(returned)
            }
        }
        return list
    }

    fun sortBy(uid: String, selectedSort: String) {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return
        }
        when (selectedSort) {
            SortType.BY_PRIORITY -> {
                goal.sortByPriority()
            }
            SortType.BY_DEADLINE -> {
                goal.sortByDeadline()
            }
            SortType.BY_COMPLETION -> {
                goal.sortByCompletion()
            }
        }
        goal.separate()
    }

    fun getCompletion(uid: String): Float {
        var goal = getGoalByUid(uid)
        if (goal == null) {
            return 0f
        }
        return goal.getCompletion()
    }

    fun sortByIsDone() {
        goals.sortBy {
            it.isDone
        }
    }
}