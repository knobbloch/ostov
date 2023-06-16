package com.knobblochsapplication.app.modules.menuone.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.FishBoneBinding
import com.knobblochsapplication.app.modules.File_system.Goal

class MenuAdapter(val listener: Listener, private val goalsList: ArrayList<Goal>):
    RecyclerView.Adapter<MenuAdapter.GoalHolder>(){

    class GoalHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = FishBoneBinding.bind(item)
        fun bind(goal: com.knobblochsapplication.app.modules.File_system.Goal, listener: Listener) = with(binding) {
            txtNamegoal.text = goal.name
            txtNamegoal.setOnClickListener (View.OnClickListener {
                listener.onBranchClick(adapterPosition, goal)
                //return@OnClickListener true
            })
            txtNamegoal.setOnLongClickListener(View.OnLongClickListener {
                listener.onLongBranchClick(adapterPosition, goal)
                return@OnLongClickListener true
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fish_bone, parent, false)


        for (goal in goalsList){
            if (goal.childList != null){
                val view2 = view.findViewById<View>(R.id.rcView) as RecyclerView
                val layoutManager = LinearLayoutManager(view2.context)
                val adap = MenuAdapter(listener, goal.childList)
                view2.setAdapter(adap)
                view2.setLayoutManager(layoutManager)
                MenuAdapter(listener, goal.childList)
            }
        }
        return GoalHolder(view)
    }

    override fun getItemCount(): Int {
        return goalsList.size
    }

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        holder.bind(goalsList[position], listener)
    }


    interface Listener {
        fun onBranchClick(position: Int, goal: Goal)
        fun onLongBranchClick(position: Int, goal: Goal)
    }

}