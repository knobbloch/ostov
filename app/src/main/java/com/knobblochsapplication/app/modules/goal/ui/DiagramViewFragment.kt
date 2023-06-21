package com.knobblochsapplication.app.modules.goal.ui

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.animation.ScaleAnimation
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

    private var mScale = 1f

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
        zoom()
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

    override fun addChildTask(uid: String) {
        (activity as GoalActivity).showCreateTaskDialogFragment()
    }

    private fun zoom(){
        GoalActivity.gestureDetector = GestureDetector(this.requireContext(), GestureListener())
        GoalActivity.mScaleGestureDetector = ScaleGestureDetector(this.requireContext(), object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {

                // firstly we will get the scale factor
                val scale = 1 - detector.scaleFactor
                val prevScale = mScale
                mScale += scale

                // we can maximise our focus to 10f only
                if (mScale > 1f) mScale = 1f
                if (mScale < 0f) mScale = 0.01f
                val scaleAnimation = ScaleAnimation(
                    1f / prevScale,
                    1f / mScale,
                    1f / prevScale,
                    1f / mScale,
                    detector.focusX,
                    detector.focusY
                )

                // duration of animation will be 0.It will
                // not change by self after that
                scaleAnimation.duration = 0
                scaleAnimation.fillAfter = true

                // initialising the scrollview
                val layout = binding.forZoom

                // we are setting it as animation
                layout.startAnimation(scaleAnimation)
                return true
            }
        })
    }

    private class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            return true
        }
    }
}
