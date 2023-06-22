package com.knobblochsapplication.app.modules.goal.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.Node
import com.knobblochsapplication.app.databinding.BoneBinding

class TaskRightSideAdapter(val listener: TaskLeftSideAdapter.Listener, private val tasksList: MutableList<Node>) :
    RecyclerView.Adapter<TaskRightSideAdapter.TaskRightSideHolder>() {

    class TaskRightSideHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = BoneBinding.bind(item)

        fun bind(task: Node, listener: TaskLeftSideAdapter.Listener) = with(binding) {
            nameTask.text = task.name
            itemView.setOnClickListener {
                listener.onTaskClick(adapterPosition, task.uid)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRightSideAdapter.TaskRightSideHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.right_bone, parent, false)
        return TaskRightSideAdapter.TaskRightSideHolder(view)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: TaskRightSideAdapter.TaskRightSideHolder, position: Int) {
        holder.bind(tasksList[position], listener)
    }


    interface Listener {
        fun onTaskClick(position: Int, uid: String)
    }
}