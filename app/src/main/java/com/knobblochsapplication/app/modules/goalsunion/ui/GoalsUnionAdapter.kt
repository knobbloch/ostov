package com.knobblochsapplication.app.modules.goalsunion.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.GoalUnionItemBinding
import com.knobblochsapplication.app.modules.goals.ui.Goal

class GoalsUnionAdapter(private val goalsUnionList: ArrayList<Goal>) :
    RecyclerView.Adapter<GoalsUnionAdapter.GoalHolder>() {

    class GoalHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = GoalUnionItemBinding.bind(item)

        fun bind(goal: Goal) = with(binding) {
            txtNamegoal.text = goal.goalName
            txtAboutgoal.text = goal.goalDescription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.goal_union_item, parent, false)
        return GoalHolder(view)
    }

    override fun getItemCount(): Int {
        return goalsUnionList.size
    }

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        holder.bind(goalsUnionList[position])
    }



}
