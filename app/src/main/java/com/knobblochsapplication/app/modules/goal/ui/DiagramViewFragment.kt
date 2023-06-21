package com.knobblochsapplication.app.modules.goal.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrdeveloper.treeview.TreeViewHolderFactory
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseFragment
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.Node
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.FragmentDiagramViewBinding
import org.koin.android.ext.android.inject

class DiagramViewFragment :
    BaseFragment<FragmentDiagramViewBinding>(R.layout.fragment_diagram_view),
    TaskRightSideAdapter.Listener, TaskLeftSideAdapter.Listener {
    private val appStorage: AppStorage by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    lateinit var adapterLeft: TaskLeftSideAdapter
    lateinit var adapterRight: TaskRightSideAdapter
    lateinit var goal2: Node

    override fun addObservers(): Unit {
        val uid = preferenceHelper.getLastSelectedGoal()
        if (uid != null) {
            val goal = appStorage.getGoalByUid(uid)
            if (goal != null) {
                goal2 = goal
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

    override fun onResume() {
        super.onResume()
        val uid = preferenceHelper.getLastSelectedGoal()
        if (uid != null) {
            val goal = appStorage.getGoalByUid(uid)
            if (goal != null) {
                goal2 = goal
                adapterRight.updateTreeNodes(
                    goal.treeViewAdapterLeft(
                        R.layout.bone_left_root,
                        R.layout.bone_left_child
                    )
                )
                adapterRight.updateTreeNodes(
                    goal.treeViewAdapterRight(
                        R.layout.bone_right_root,
                        R.layout.bone_right_child

                    )
                )
            }
        }
    }


    override fun setUpClicks() {
        // right
        binding.recyclerRight.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerRight.isNestedScrollingEnabled = false
        val factoryRight =
            TreeViewHolderFactory { v: View, layout: Int ->
                if (layout == R.layout.bone_right_child) {
                    adapterRight.createViewHolderForChild(v)
                } else {
                    adapterRight.createViewHolderForRoot(v)
                }
            }
        adapterRight = TaskRightSideAdapter(this, factoryRight)
        binding.recyclerRight.adapter = adapterRight
        adapterRight.updateTreeNodes(
            goal2.treeViewAdapterRight(
                R.layout.bone_right_root,
                R.layout.bone_right_child
            )
        );

        // left
        binding.recyclerLeft.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerLeft.isNestedScrollingEnabled = false
        val factoryLeft =
            TreeViewHolderFactory { v: View, layout: Int ->
                if (layout == R.layout.bone_left_child) {
                    adapterLeft.createViewHolderForChild(v)
                } else {
                    adapterLeft.createViewHolderForRoot(v)
                }
            }
        adapterLeft = TaskLeftSideAdapter(this, factoryLeft)
        binding.recyclerLeft.adapter = adapterLeft
        adapterLeft.updateTreeNodes(
            goal2.treeViewAdapterLeft(
                R.layout.bone_left_root,
                R.layout.bone_left_child
            )
        );
    }

    override fun onTaskClick(uid: String) {
        (activity as GoalActivity).showTaskDialog(uid)
    }
}
