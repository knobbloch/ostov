package com.knobblochsapplication.app.modules.goal.ui

import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.amrdeveloper.treeview.TreeNode
import com.amrdeveloper.treeview.TreeViewAdapter
import com.amrdeveloper.treeview.TreeViewHolder
import com.amrdeveloper.treeview.TreeViewHolderFactory
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.BoneLeftChildBinding
import com.knobblochsapplication.app.databinding.BoneLeftRootBinding


class TaskLeftSideAdapter(
    val listener: TaskLeftSideAdapter.Listener,
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
                    if (node.isExpanded) R.drawable.ic_arrow_drop_down else R.drawable.ic_arrow_right
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

        val binding = BoneLeftRootBinding.bind(itemView)

        override fun bindTreeNode(node: TreeNode) = with(binding) {
            val pair = node.value as TreeTask
            name.text = pair.name

            if (pair.isDone){
                imageBoneRootLeft.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_done))
            } else if ((pair.priority >= 1) && (pair.priority <= 4)){
                imageBoneRootLeft.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_1))
            } else if ((pair.priority >= 5) && (pair.priority <= 8)){
                imageBoneRootLeft.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_2))
            } else {
                imageBoneRootLeft.setImageDrawable(AppCompatResources.getDrawable(name.context, R.drawable.bone_priority_3))
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
                listener.addChildTask(pair.uid)
            }
        }
    }

    interface Listener {
        fun onTaskClick(uid: String)
        fun addChildTask(uid: String)
    }
}