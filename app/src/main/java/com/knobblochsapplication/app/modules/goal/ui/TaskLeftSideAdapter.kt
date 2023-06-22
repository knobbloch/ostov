package com.knobblochsapplication.app.modules.goal.ui

import android.view.View
import com.amrdeveloper.treeview.TreeNode
import com.amrdeveloper.treeview.TreeViewAdapter
import com.amrdeveloper.treeview.TreeViewHolder
import com.amrdeveloper.treeview.TreeViewHolderFactory
import com.knobblochsapplication.app.databinding.BoneLeftChildBinding
import com.knobblochsapplication.app.databinding.BoneLeftRootBinding


class TaskLeftSideAdapter(
    val listener: Listener,
    factory: TreeViewHolderFactory,
) : TreeViewAdapter(factory) {

    fun createViewHolderForChild(itemView: View): TaskViewHolderChild {
        var i = TaskViewHolderChild(itemView)
        i.nodePadding = 50
        return i
    }

    fun createViewHolderForRoot(itemView: View): TaskViewHolderRoot {
        var i = TaskViewHolderRoot(itemView)
        i.nodePadding = 0
        return i
    }

    inner class TaskViewHolderChild(itemView: View) : TreeViewHolder(itemView) {

        val binding = BoneLeftChildBinding.bind(itemView)

        override fun bindTreeNode(node: TreeNode) = with(binding) {
            val pair = node.value as TreeTask
            name.text = pair.name
            layout.setPadding(
                0,
                itemView.paddingTop,
                node.level * 50,
                itemView.paddingBottom
            )

            btnTaskMenu.setOnClickListener {
                listener.onTaskClick(pair.uid)
            }
            btnAdd.setOnClickListener {
                listener.addChildTask(pair.uid)
            }
        }
    }

    inner class TaskViewHolderRoot(itemView: View) : TreeViewHolder(itemView) {

        val binding = BoneLeftRootBinding.bind(itemView)

        override fun bindTreeNode(node: TreeNode) = with(binding) {
            val pair = node.value as TreeTask
            name.text = pair.name

            btnTaskMenu.setOnClickListener {
                listener.onTaskClick(pair.uid)
            }
            btnAdd.setOnClickListener {
                listener.addChildTask(pair.uid)
            }
        }
    }

    interface Listener {
        fun onTaskClick(uid: String)
        fun addChildTask(uid: String)
    }
}