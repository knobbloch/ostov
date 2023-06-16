package com.knobblochsapplication.app.modules.goals.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.GoalItemBinding

class GoalsAdapter(val listener: Listener, private val goalsList: ArrayList<Goal>) :
    RecyclerView.Adapter<GoalsAdapter.GoalHolder>() {


    class GoalHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = GoalItemBinding.bind(item)


        fun bind(goal: Goal, listener: Listener) = with(binding) {
            txtNamegoal.text = goal.goalName
            txtAboutgoal.text = goal.goalDescription
            itemView.setOnClickListener {
                listener.onGoalClick(adapterPosition)
            }
            itemView.setOnLongClickListener(View.OnLongClickListener {
                listener.onLongGoalClick(adapterPosition)
                return@OnLongClickListener true
            })

            deadline.text = goal.goalDeadline
            itemView.setOnClickListener {
                listener.onGoalClick(adapterPosition)
            }
            btnDelete.setOnClickListener {
                listener.onBtnDeleteClick(adapterPosition)
            }
            btnEdit.setOnClickListener {
                listener.onBtnEditClick(adapterPosition)
            }
            btnChangeLevel.setOnClickListener {
                listener.onBtnChangeLevelClick(adapterPosition)
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
        fun onLongGoalClick(position: Int)
        fun onBtnDeleteClick(position: Int)
        fun onBtnEditClick(position: Int)
        fun onBtnChangeLevelClick(position: Int)
    }

}