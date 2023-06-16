package com.knobblochsapplication.app.modules.goalsunion.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.Node
import com.knobblochsapplication.app.databinding.GoalUnionItemBinding

class GoalsUnionAdapter(val listener: Listener, private val goalsList: MutableList<Node>) :
    RecyclerView.Adapter<GoalsUnionAdapter.GoalHolder>() {

    class GoalHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = GoalUnionItemBinding.bind(item)

        fun bind(goal: Node, listener: Listener) = with(binding) {
            txtNamegoal.text = goal.name
            txtAboutgoal.text = goal.description
            deadline.text = goal.deadline
            radio.setOnCheckedChangeListener { _, _ ->
                listener.onBtnRadioClick(adapterPosition, goal.uid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.goal_union_item, parent, false)
        return GoalHolder(view)
    }

    override fun getItemCount(): Int {
        return goalsList.size
    }

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        holder.bind(goalsList[position], listener)
    }

    interface Listener {
        fun onBtnRadioClick(position: Int, uid: String)
    }
}
