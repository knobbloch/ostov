package com.knobblochsapplication.app.modules.goal.ui

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.amrdeveloper.treeview.TreeNode
import com.amrdeveloper.treeview.TreeViewAdapter
import com.amrdeveloper.treeview.TreeViewHolder
import com.amrdeveloper.treeview.TreeViewHolderFactory
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.TaskListItemBinding


class TaskListTreeViewAdapter(
    val listener: TaskListTreeViewAdapter.Listener,
    factory: TreeViewHolderFactory
) : TreeViewAdapter(factory) {

    fun createViewHolder(itemView: View): TaskViewHolder {
        return TaskViewHolder(itemView)
    }

    inner class TaskViewHolder(itemView: View) : TreeViewHolder(itemView) {

        val binding = TaskListItemBinding.bind(itemView)

        override fun bindTreeNode(node: TreeNode) = with(binding) {
            super.bindTreeNode(node)
            val pair = node.value as TreeTask
            name.text = pair.name

            if (pair.isDone){
                priority.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.task_done))
            } else if ((pair.priority >= 1) && (pair.priority <= 4)){
                priority.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.task_priority_1))
            } else if ((pair.priority >= 5) && (pair.priority <= 8)){
                priority.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.task_priority_2))
            } else {
                priority.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.task_priority_3))
            }

            // change state icon
            if (node.children.isEmpty()) {
                state.visibility = View.INVISIBLE
            } else {
                state.visibility = View.VISIBLE
                val stateIcon =
                    if (node.isExpanded) R.drawable.ic_arrow_drop_down else R.drawable.ic_arrow_right
                state.setImageResource(stateIcon)
            }
            btnTaskMenu.setOnClickListener {
                listener.onTaskClick(pair.uid)
            }

            btnAdd.setOnClickListener {
                listener.addTask(pair.uid)
            }
        }
    }

    interface Listener {
        fun onTaskClick(uid: String)
        fun addTask(uid: String)
    }
}