package com.knobblochsapplication.app.modules.goal.ui

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.amrdeveloper.treeview.TreeNode
import com.amrdeveloper.treeview.TreeViewAdapter
import com.amrdeveloper.treeview.TreeViewHolder
import com.amrdeveloper.treeview.TreeViewHolderFactory
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.BoneRightChildBinding
import com.knobblochsapplication.app.databinding.BoneRightRootBinding

class TaskRightSideAdapter(
    val listener: TaskRightSideAdapter.Listener,
    factory: TreeViewHolderFactory,
) : TreeViewAdapter(factory) {

    fun createViewHolderForChild(itemView: View): TaskViewHolderChild {
        var i = TaskViewHolderChild(itemView)
//        i.nodePadding = 50
        return i
    }

    fun createViewHolderForRoot(itemView: View): TaskViewHolderRoot {
        var i = TaskViewHolderRoot(itemView)
//        i.nodePadding = 0
        return i
    }

    inner class TaskViewHolderChild(itemView: View) : TreeViewHolder(itemView) {

        val binding = BoneRightChildBinding.bind(itemView)

        override fun bindTreeNode(node: TreeNode) = with(binding) {
            super.bindTreeNode(node)
            val pair = node.value as TreeTask
            name.text = pair.name

            if (pair.isDone){
                verticalBone.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_done))
                horizontalBone.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_done))
            } else if ((pair.priority >= 1) && (pair.priority <= 4)){
                verticalBone.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_1))
                horizontalBone.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_1))
            } else if ((pair.priority >= 5) && (pair.priority <= 8)){
                verticalBone.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_2))
                horizontalBone.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_2))
            } else{
                verticalBone.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_3))
                horizontalBone.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_3))
            }

            // change state icon
            if (node.children.isEmpty()) {
                state.visibility = View.INVISIBLE
            } else {
                state.visibility = View.VISIBLE
                val stateIcon =
                    if (node.isExpanded) R.drawable.ic_arrow_right else R.drawable.ic_arrow_drop_down
                state.setImageResource(stateIcon)
            }

            btnTaskMenu.setOnClickListener {
                listener.onTaskClick(pair.uid)
            }
            btnAdd.setOnClickListener {
                listener.addChildTask(pair.uid)
            }
        }
    }

    inner class TaskViewHolderRoot(itemView: View) : TreeViewHolder(itemView) {

        val binding = BoneRightRootBinding.bind(itemView)

        override fun bindTreeNode(node: TreeNode) = with(binding) {
            super.bindTreeNode(node)
            val pair = node.value as TreeTask
            name.text = pair.name

            if (pair.isDone){
                image.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_done))
            } else if ((pair.priority >= 1) && (pair.priority <= 4)){
                image.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_1))
            } else if ((pair.priority >= 5) && (pair.priority <= 8)){
                image.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_2))
            } else {
                image.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_3))
            }

            // change state icon
            if (node.children.isEmpty()) {
                state.visibility = View.INVISIBLE
            } else {
                state.visibility = View.VISIBLE
                val stateIcon =
                    if (node.isExpanded) R.drawable.ic_arrow_right else R.drawable.ic_arrow_drop_down
                state.setImageResource(stateIcon)
            }



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