package com.knobblochsapplication.app.appcomponents.utility

import android.content.Context
import com.google.gson.GsonBuilder
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

}