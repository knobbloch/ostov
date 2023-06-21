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


class ListViewFragment : BaseFragment<FragmentListViewBinding>(R.layout.fragment_list_view), TaskTreeViewAdapter.Listener {
    private val preferenceHelper: PreferenceHelper by inject()
    private val appStorage: AppStorage by inject()
    var lastSelectedGoalUid: String? = null
    var treeViewAdapter: TaskTreeViewAdapter? = null
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

        //tree view
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.isNestedScrollingEnabled = false
        val factory =
            TreeViewHolderFactory { v: View, layout: Int ->
                treeViewAdapter!!.createViewHolder(v)
            }
        treeViewAdapter = TaskTreeViewAdapter(this, factory)
        binding.recyclerView.adapter = treeViewAdapter
        treeViewAdapter!!.updateTreeNodes(goal.treeViewAdapter());

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
            treeViewAdapter!!.updateTreeNodes(goal.treeViewAdapter());
        }
    }

    override fun setUpClicks() {

    }

    override fun onTaskClick(uid: String) {
        (activity as GoalActivity).showTaskDialog(uid)
    }
}
