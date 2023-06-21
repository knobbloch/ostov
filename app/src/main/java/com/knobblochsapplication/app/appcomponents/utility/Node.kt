package com.knobblochsapplication.app.appcomponents.utility

import android.os.Parcelable
import com.amrdeveloper.treeview.TreeNode
import com.knobblochsapplication.app.modules.goal.ui.TreeTask
import kotlinx.parcelize.Parcelize
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
        var treeNode = TreeNode(TreeTask(node.name, node.uid, height), child)
        if (height == 0) {
            treeNode = TreeNode(TreeTask(node.name, node.uid, height), layout)
        }
        if (node.tasks.isEmpty()) {
            return treeNode
        }
        for (task in node.tasks) {
            treeNode.addChild(treeNodeAdapter(task, layout, child, height + 1))
        }
        return treeNode
    }

//    fun addChildTask(
//        taskUid: String,
//        name: String,
//        deadline: String?,
//        priority: Int,
//        description: String?,
//    ) {
//        val taskOld = getTaskByUid(taskUid)
//        if (taskOld == null) {
//            return
//        }
//        val task = Node(
//            UUID.randomUUID().toString(),
//            name = name,
//            deadline = deadline,
//            priority = priority,
//            isDone = false,
//            description = description,
//            tasks = mutableListOf()
//        )
//        taskOld.tasks.add(task)
//        taskOld.separate()
//        saveToFile(taskOld)
//    }

}