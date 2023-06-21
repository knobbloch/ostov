package com.knobblochsapplication.app.modules.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.Node
import com.knobblochsapplication.app.databinding.GoalItemBinding

class GoalsAdapter(val listener: Listener, private val goalsList: MutableList<Node>) :
    RecyclerView.Adapter<GoalsAdapter.GoalHolder>() {

    class GoalHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = GoalItemBinding.bind(item)

        fun bind(goal: Node, listener: Listener) = with(binding) {
            txtNamegoal.text = goal.name
            txtAboutgoal.text = goal.description
            deadline.text = goal.deadline
            itemView.setOnClickListener {
                listener.onGoalClick(bindingAdapterPosition)
            }
            btnDelete.setOnClickListener {
                listener.onBtnDeleteClick(bindingAdapterPosition, goal.uid)
            }
            btnEdit.setOnClickListener {
                listener.onBtnEditClick(bindingAdapterPosition)
            }
            btnChangeLevel.setOnClickListener {
                listener.onBtnChangeLevelClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.goal_item, parent, false)
        return GoalHolder(view)
    }

    override fun getItemCount(): Int {
        return goalsList.size
    }

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        holder.bind(goalsList[position], listener)
    }


    interface Listener {
        fun onGoalClick(position: Int)
        fun onBtnDeleteClick(position: Int, uid: String)
        fun onBtnEditClick(position: Int)
        fun onBtnChangeLevelClick(position: Int)
    }

}