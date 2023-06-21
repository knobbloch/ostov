package com.knobblochsapplication.app.modules.goal.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.Node
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityMainMenuBinding
import com.knobblochsapplication.app.modules.diagramview.ui.DiagramViewActivity
import com.knobblochsapplication.app.modules.downloadlist.ui.DownloadListActivity
import com.knobblochsapplication.app.modules.sort.ui.SortActivity
import org.koin.android.ext.android.inject


class GoalActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainMenuBinding
    private val preferenceHelper: PreferenceHelper by inject()
    private val appStorage: AppStorage by inject()
    lateinit var lastSelectedGoalUid: String
    lateinit var lastSelectedTaskUid: String
    lateinit var adapter: TaskListTreeViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.addGoalBtn.setOnClickListener {
            showCreateTaskDialogFragment(null)
        }
    }

    override fun onResume() {
        super.onResume()
        val uid = preferenceHelper.getLastSelectedGoal()
        if (uid !== null) {
            lastSelectedGoalUid = uid
        }
        if (preferenceHelper.isDiagramSelected()) {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragmentContainer, DiagramViewFragment(), "diagram_view")
            ft.commit()
        } else {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragmentContainer, ListViewFragment(), "list_view")
            ft.commit()
        }
    }

    fun onClickGoDiagramView(item: MenuItem) {
        val intent = Intent(this, DiagramViewActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoSortType(item: MenuItem) {
        val intent = Intent(this, SortActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoDownloadList(item: MenuItem) {
        val intent = Intent(this, DownloadListActivity::class.java)
        startActivity(intent)
    }

    fun updateView() {
        val uid = preferenceHelper.getLastSelectedGoal()
        var goal: Node?
        if (uid == null) {
            return
        }
        goal = appStorage.getGoalByUid(lastSelectedGoalUid)
        if (goal == null) {
            return
        }
        val allFragments: List<Fragment> = supportFragmentManager.fragments
        for (fragment in allFragments) {
            if (fragment is ListViewFragment) {
                fragment.treeViewAdapter!!.updateTreeNodes(goal.treeViewAdapter(R.layout.task_list_item))
                continue
            }
            if (fragment is DiagramViewFragment) {
                fragment.adapterLeft.updateTreeNodes(
                    goal.treeViewAdapterLeft(
                        R.layout.bone_left_root,
                        R.layout.bone_left_child
                    )
                )
                fragment.adapterRight.updateTreeNodes(
                    goal.treeViewAdapterRight(
                        R.layout.bone_right_root,
                        R.layout.bone_right_child
                    )
                )
            }
        }
    }

    fun showTaskDialog(taskUid: String) {
        lastSelectedTaskUid = taskUid
        MaterialAlertDialogBuilder(this)
            .setNeutralButton(R.string.lbl21) { dialog, _ ->
                dialog.dismiss()
            }
            .setItems(R.array.dialog_actions_with_task) { dialog, which ->
                when (which) {
                    0 -> {
                        showEditTaskDialogFragment()
                        dialog.dismiss()
                    }
                    1 -> {
                        showDialogDeleteTask()
                        dialog.dismiss()
                    }
                    2 -> {
                        showDialogSeparateTarget()
                        dialog.dismiss()
                    }
                }
            }
            .setTitle(R.string.change_action)
            .create()
            .show()
    }

    fun showCreateTaskDialogFragment(uid: String?) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        val newFragment: DialogFragment = CreateTaskDialogFragment.newInstance(uid)
        newFragment.show(ft, "dialog")
    }

    fun showEditTaskDialogFragment() {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        val newFragment: DialogFragment = EditTaskDialogFragment.newInstance(lastSelectedTaskUid)
        newFragment.show(ft, "dialog")
    }

    fun showDialogSeparateTarget() {
        var checkedId: Int = 0
        MaterialAlertDialogBuilder(this)
            .setNegativeButton(R.string.lbl21) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl36) { dialog, _ ->
                when (checkedId) {
                    0 -> {
                        appStorage.duplicateGoal(lastSelectedGoalUid, lastSelectedTaskUid)
                    }
                    1 -> {
                        appStorage.createGoalFromTask(lastSelectedGoalUid, lastSelectedTaskUid)
                    }
                    2 -> {
                        appStorage.createGoalFromTaskWithoutChild(lastSelectedGoalUid, lastSelectedTaskUid)
                    }
                }
                updateView()
                dialog.dismiss()
            }
            .setSingleChoiceItems(
                R.array.dialog_separate_target,
                0
            ) { dialog, which ->
                checkedId = which
            }
            .setTitle(R.string.choise_action_when_separate_target)
            .show()
    }

    fun showDialogDeleteTask() {
        var checkedId: Int = 0
        MaterialAlertDialogBuilder(this)
            .setNegativeButton(R.string.lbl21) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl22) { dialog, _ ->
                when (checkedId) {
                    0 -> {
                        appStorage.deleteTask(lastSelectedGoalUid, lastSelectedTaskUid)
                    }
                    1 -> {
                        appStorage.deleteTaskTransferChild(lastSelectedGoalUid, lastSelectedTaskUid)
                    }
                }
                updateView()
                dialog.dismiss()
            }
            .setSingleChoiceItems(
                R.array.dialog_delete_task,
                0
            ) { dialog, which ->
                checkedId = which
            }
            .setTitle(R.string.lbl34)
            .show()
    }

}