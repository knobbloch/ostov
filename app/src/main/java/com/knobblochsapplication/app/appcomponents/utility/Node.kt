package com.knobblochsapplication.app.appcomponents.utility

import android.os.Parcelable
import com.amrdeveloper.treeview.TreeNode
import com.knobblochsapplication.app.R
import kotlinx.parcelize.Parcelize

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

    fun separete() {
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

    fun treeViewAdapter(): MutableList<TreeNode> {
        if (tasks.size == 0) {
            return mutableListOf()
        }
        val subtasks = mutableListOf<TreeNode>()
        for (task in tasks) {
            subtasks.add(treeNodeAdapter(task))
        }
        return subtasks
    }

    private fun treeNodeAdapter(node: Node): TreeNode {
        val treeNode = TreeNode(Pair(node.name, node.uid), R.layout.task_list_item)
        if (node.tasks.isEmpty()) {
            return treeNode
        }
        for (task in node.tasks) {
            treeNode.addChild(treeNodeAdapter(task))
        }
        return treeNode
    }
}