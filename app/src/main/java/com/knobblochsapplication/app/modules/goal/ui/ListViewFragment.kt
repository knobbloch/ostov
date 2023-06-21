package com.knobblochsapplication.app.modules.goal.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrdeveloper.treeview.TreeViewHolderFactory
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseFragment
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.FragmentListViewBinding
import org.koin.android.ext.android.inject


class ListViewFragment : BaseFragment<FragmentListViewBinding>(R.layout.fragment_list_view), TaskListTreeViewAdapter.Listener {
    private val preferenceHelper: PreferenceHelper by inject()
    private val appStorage: AppStorage by inject()
    var lastSelectedGoalUid: String? = null
    var treeViewAdapter: TaskListTreeViewAdapter? = null
    override fun addObservers() {
        super.addObservers()
        lastSelectedGoalUid = preferenceHelper.getLastSelectedGoal()
        if (lastSelectedGoalUid == null) {
            return
        }

        val goal = appStorage.getGoalByUid(lastSelectedGoalUid!!)
        if (goal == null) {
            return
        }
        binding.goalName.text = goal.name
        binding.deadline.text = goal.deadline
        binding.priority.text = goal.priority.toString()
        if (!goal.isDone) {
            binding.isDone.setText(R.string.no)
        } else {
            binding.isDone.setText(R.string.yes)
        }
        if (goal.tasks.size == 0) {
            binding.empty.visibility = View.VISIBLE
            binding.list.visibility = View.GONE
        } else {
            binding.empty.visibility = View.GONE
            binding.list.visibility = View.VISIBLE
        }


        //tree view
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.isNestedScrollingEnabled = false
        val factory =
            TreeViewHolderFactory { v: View, layout: Int ->
                treeViewAdapter!!.createViewHolder(v)
            }
        treeViewAdapter = TaskListTreeViewAdapter(this, factory)
        binding.recyclerView.adapter = treeViewAdapter
        treeViewAdapter!!.updateTreeNodes(goal.treeViewAdapter(R.layout.task_list_item));

    }


    override fun onResume() {
        super.onResume()
        val uid = preferenceHelper.getLastSelectedGoal()
        if (uid !== null) {
            val goal = appStorage.getGoalByUid(lastSelectedGoalUid!!)
            if (goal == null) {
                return
            }
            lastSelectedGoalUid = uid
            treeViewAdapter!!.updateTreeNodes(goal.treeViewAdapter(R.layout.task_list_item));
        }
    }

    override fun setUpClicks() {

    }

    override fun onTaskClick(uid: String) {
        (activity as GoalActivity).showTaskDialog(uid)
    }
}
