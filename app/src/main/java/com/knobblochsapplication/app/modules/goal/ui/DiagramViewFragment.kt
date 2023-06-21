package com.knobblochsapplication.app.modules.goal.ui

import androidx.recyclerview.widget.LinearLayoutManager
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseFragment
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.FragmentDiagramViewBinding
import org.koin.android.ext.android.inject

class DiagramViewFragment: BaseFragment<FragmentDiagramViewBinding>(R.layout.fragment_diagram_view),
TaskLeftSideAdapter.Listener {
    private val appStorage: AppStorage by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    lateinit var adapter: TaskLeftSideAdapter
    lateinit var adapterRight: TaskRightSideAdapter


    override fun addObservers(): Unit {
        val uid = preferenceHelper.getLastSelectedGoal()
        if (uid != null) {
            val goal = appStorage.getGoalByUid(uid)
            if (goal != null) {
                adapter = TaskLeftSideAdapter(this, goal.left)
                adapterRight = TaskRightSideAdapter(this, goal.right)

            }
        }
    }

    override fun onInitialized() {
        super.onInitialized()
        val uid = preferenceHelper.getLastSelectedGoal()
        if (uid != null) {
            val goal = appStorage.getGoalByUid(uid)
            if (goal != null) {
                binding.goalName.text = goal.name
            }
        }
    }



    override fun setUpClicks() {
        binding.recyclerLeft.layoutManager = LinearLayoutManager(context)
        binding.recyclerLeft.adapter = adapter
        binding.recyclerRight.layoutManager = LinearLayoutManager(context)
        binding.recyclerRight.adapter = adapterRight
    }

    override fun onTaskClick(position: Int, uid: String) {
//        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
//        ft.addToBackStack(null)
//        val newFragment: DialogFragment = EditTaskDialogFragment.newInstance(position, uid)
//        newFragment.show(ft, "dialog")
    }
}
