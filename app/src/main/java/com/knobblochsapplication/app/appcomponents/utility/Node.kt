package com.knobblochsapplication.app.appcomponents.utility

import android.os.Parcelable
import com.amrdeveloper.treeview.TreeNode
import com.knobblochsapplication.app.modules.goal.ui.TreeTask
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Node(
    val uid: String,
    var name: String?,
    var deadline: String?,
    var priority: Int,
    var isDone: Boolean,
    var description: String?,
    var tasks: MutableList<Node>,
) : Parcelable {
    val left: MutableList<Node> = mutableListOf()
    val right: MutableList<Node> = mutableListOf()

    fun separate() {
        left.clear()
        right.clear()
        for ((index, item) in tasks.withIndex()) {
            if (index % 2 == 0) {
                right.add(item)
            } else {
                left.add(item)
            }
        }

    }

    fun getTaskByUid(uid: String): Node? {
        return getTaskById(uid, this)
    }

    private fun getTaskById(uid: String, node: Node): Node? {
        if (node.uid == uid) {
            return node
        }
        if (node.tasks.isEmpty()) {
            return null
        }
        for (task in node.tasks) {
            val v = getTaskById(uid, task)
            if (v != null) {
                return v
            }
        }
        return null
    }

    fun deleteTask(uid: String): Node? {
        return deleteTask(uid, this)
    }

    private fun deleteTask(uid: String, node: Node): Node? {
        if (node.uid == uid) {
            return node
        }
        if (node.tasks.isEmpty()) {
            return null
        }
        for (task in node.tasks) {
            val v = deleteTask(uid, task)
            if (v != null) {
                node.tasks.remove(v)
                return null

            }
        }
        return null
    }

    fun deleteTaskTransferChild(uid: String): Node? {
        return deleteTaskTransferChild(uid, this)
    }

    private fun deleteTaskTransferChild(uid: String, node: Node): Node? {
        if (node.uid == uid) {
            return node
        }
        if (node.tasks.isEmpty()) {
            return null
        }
        for (task in node.tasks) {
            val v = deleteTaskTransferChild(uid, task)
            if (v != null) {
                node.tasks.addAll(v.tasks)
                node.tasks.remove(v)
                return null
            }
        }
        return null
    }

    fun duplicate(): Node {
        return duplicate(this)
    }

    private fun duplicate(node: Node): Node {
        var newNode = Node(
            UUID.randomUUID().toString(),
            node.name,
            node.deadline,
            node.priority,
            node.isDone,
            node.description,
            mutableListOf()
        )
        if (node.tasks.size == 0) {
            return newNode
        }
        for (item in node.tasks) {
            newNode.tasks.add(duplicate(item))
        }
        return newNode
    }

    fun treeViewAdapterLeft(layout: Int, child: Int = 0): MutableList<TreeNode> {
        if (left.size == 0) {
            return mutableListOf()
        }
        val subtasks = mutableListOf<TreeNode>()

        for (task in left) {
            if (child == 0) {
                // not defined
                subtasks.add(treeNodeAdapter(task, layout, layout, 0))
            } else {
                subtasks.add(treeNodeAdapter(task, layout, child, 0))
            }
        }
        return subtasks
    }

    fun treeViewAdapterRight(layout: Int, child: Int = 0): MutableList<TreeNode> {
        if (right.size == 0) {
            return mutableListOf()
        }
        val subtasks = mutableListOf<TreeNode>()

        for (task in right) {
            if (child == 0) {
                // not defined
                subtasks.add(treeNodeAdapter(task, layout, layout, 0))
            } else {
                subtasks.add(treeNodeAdapter(task, layout, child, 0))
            }
        }
        return subtasks
    }

    fun treeViewAdapter(layout: Int, child: Int = 0): MutableList<TreeNode> {
        if (tasks.size == 0) {
            return mutableListOf()
        }
        val subtasks = mutableListOf<TreeNode>()

        for (task in tasks) {
            if (child == 0) {
                // not defined
                subtasks.add(treeNodeAdapter(task, layout, layout, 0))
            } else {
                subtasks.add(treeNodeAdapter(task, layout, child, 0))
            }
        }
        return subtasks
    }

    private fun treeNodeAdapter(node: Node, layout: Int, child: Int, height: Int): TreeNode {
        var treeNode = TreeNode(TreeTask(node.name, node.uid, height, node.priority, node.isDone), child)
        if (height == 0) {
            treeNode = TreeNode(TreeTask(node.name, node.uid, height, node.priority, node.isDone), layout)
        }
        if (node.tasks.isEmpty()) {
            return treeNode
        }
        for (task in node.tasks) {
            treeNode.addChild(treeNodeAdapter(task, layout, child, height + 1))
        }
        return treeNode
    }

    fun sortByPriority(): Node {
        return sortByPriority(this)
    }

    private fun sortByPriority(node: Node): Node {
        node.tasks.sortBy {
            it.priority
        }
        for (item in node.tasks) {
            sortByPriority(item)
        }
        return node
    }

    fun sortByDeadline(): Node {
        return sortByDeadline(this)
    }

    private fun sortByDeadline(node: Node): Node {
        val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        node.tasks.sortBy {
            if (it.deadline == null || it.deadline == "") {
                Date(0)
            } else {
                outputDateFormat.parse(it.deadline!!)
            }
        }
        for (item in node.tasks) {
            sortByDeadline(item)
        }
        return node
    }

    fun sortByPriorityDeadline(): Node {
        return sortByPriorityDeadline(this)
    }

    private fun sortByPriorityDeadline(node: Node): Node {
        val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        node.tasks.sortWith(compareBy({it.priority},{
            if (it.deadline == null || it.deadline == "") {
                Date(0)
            } else {
                outputDateFormat.parse(it.deadline!!)
            }
        }))
        for (item in node.tasks) {
            sortByDeadline(item)
        }
        return node
    }

    fun sortByCompletion(): Node {
        return sortByCompletion(this)
    }

    private fun sortByCompletion(node: Node): Node {
        node.tasks.sortBy {
            if (it.tasks.size == 0) {
                it.isDone.compareTo(false).toFloat()
            } else {
                it.tasks.count { it.isDone }.toFloat() / it.tasks.size
            }
        }
        if (node.tasks.size == 0) {
            return node
        }
        for (item in node.tasks) {
            sortByCompletion(item)
        }
        return node
    }

    fun markIsDone(): Node {
        return markIsDone(this)
    }

    private fun markIsDone(node: Node): Node {
        if (node.tasks.size == 0) {
            node.isDone = true
            return node
        }
        for (item in node.tasks) {
            markIsDone(item)
        }
        node.isDone = true
        return node
    }


    fun getCompletion(): Float {
        return getCompletion(this)*100
    }

    private fun getCompletion(node: Node): Float {
        if (node.tasks.size == 0) {
            return node.isDone.compareTo(false).toFloat()
        }
        if (node.isDone){
            return node.isDone.compareTo(false).toFloat()
        }
        val percent = mutableListOf<Float>()
        for (item in node.tasks) {
            percent.add(getCompletion(item))
        }
        return percent.sum() / percent.size
    }

    fun getTaskDeadlineToday(): MutableList<Node> {
        return getTaskDeadlineToday(this)
    }

    private fun getTaskDeadlineToday(node: Node): MutableList<Node> {
        var list = mutableListOf<Node>()
        val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        if ((outputDateFormat.format(Date()) == node.deadline) && (node.isDone == false)) {
            list.add(node)
        }
        for (item in node.tasks) {
            var returned = getTaskDeadlineToday(item)
            if (returned.size != 0) {
                list.addAll(returned)
            }
        }
        return list
    }
}