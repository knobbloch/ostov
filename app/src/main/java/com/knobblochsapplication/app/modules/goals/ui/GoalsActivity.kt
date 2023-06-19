package com.knobblochsapplication.app.modules.goals.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalsBinding
import com.knobblochsapplication.app.modules.File_system.File_Manager
import com.knobblochsapplication.app.modules.File_system.Goal
import com.knobblochsapplication.app.modules.goals.data.viewmodel.GoalsVM
import com.knobblochsapplication.app.modules.goalsunion.ui.GoalsUnionActivity
import com.knobblochsapplication.app.modules.menuone.ui.MainActivity


class GoalsActivity : BaseActivity<ActivityGoalsBinding>(R.layout.activity_goals),
    GoalsAdapter.Listener {

    private val viewModel: GoalsVM by viewModels<GoalsVM>()
    val goalsList : ArrayList<Goal> = ArrayList<Goal>()
    val adapter = GoalsAdapter(this, goalsList)


    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.goalsVM = viewModel
        setContentView(binding.root)
        rebuildList()
    }

    override fun onResume() : Unit {
        super.onResume()
        rebuildList()
    }

    companion object {
        const val TAG: String = "GOALS_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, GoalsActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }

    override fun setUpClicks(): Unit {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.addGoalBtn.setOnClickListener {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.addToBackStack(null)
            val newFragment: DialogFragment = CreateGoalDialogFragment.newInstance()
            newFragment.show(ft, "dialog")
        }
        binding.rcView.layoutManager = LinearLayoutManager(this@GoalsActivity)
        binding.rcView.adapter = adapter
    }

    override fun onGoalClick(position: Int) {
        val destIntent = Intent(this@GoalsActivity, MainActivity::class.java)
        destIntent.putExtra("goalId", goalsList[position].id as Int)
        startActivity(destIntent)
    }


    override fun onBtnDeleteClick(position: Int) {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.msg6))
            .setNegativeButton(R.string.lbl21) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl22) { dialog, _ ->
                File_Manager.Delete_goal(goalsList[position].id)
                this.goalsList.removeAt(position)
                adapter.notifyItemRemoved(position)
                dialog.dismiss()
            }
            .show()
    }

    override fun onBtnEditClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        val newFragment: DialogFragment = EditGoalDialogFragment.newInstance(position)

        newFragment.arguments = bundle
        newFragment.show(ft, "dialog")

    }

    override fun onBtnChangeLevelClick(position: Int) {
        val destIntent = Intent(this@GoalsActivity, GoalsUnionActivity::class.java)
        destIntent.putExtra("prev", goalsList[position].id)
        startActivity(destIntent)
    }

    fun rebuildList() {
        goalsList.clear()
        for (id in File_Manager.listFiles())
            goalsList.add(File_Manager.Find_task_by_id(id, id))
        adapter.notifyDataSetChanged()
    }
}
