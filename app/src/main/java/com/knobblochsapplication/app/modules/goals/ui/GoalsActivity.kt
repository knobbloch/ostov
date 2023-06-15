package com.knobblochsapplication.app.modules.goals.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalsBinding
import com.knobblochsapplication.app.modules.goals.data.viewmodel.GoalsVM


class GoalsActivity : BaseActivity<ActivityGoalsBinding>(R.layout.activity_goals),
    GoalsAdapter.Listener {
    private val viewModel: GoalsVM by viewModels<GoalsVM>()
    val goalsList = ArrayList<Goal>()
    val adapter = GoalsAdapter(this, goalsList)

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.goalsVM = viewModel
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        setContentView(binding.root)
        val divider = MaterialDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL)
        divider.isLastItemDecorated = false
        binding.rcView.addItemDecoration(divider)
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

    companion object {
        const val TAG: String = "GOALS_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, GoalsActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }


    override fun onGoalClick(position: Int) {
        Toast.makeText(this, "переходим к просмотру подзадач", Toast.LENGTH_LONG).show()
    }

    override fun onBtnDeleteClick(position: Int) {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.msg6))
            .setNegativeButton(R.string.lbl21) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl22) { dialog, _ ->
                this.goalsList.removeAt(position)
                adapter.notifyItemRemoved(position)
                dialog.dismiss()
            }
            .show()
    }

    override fun onBtnEditClick(position: Int) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        val newFragment: DialogFragment = EditGoalDialogFragment.newInstance(position)
        newFragment.show(ft, "dialog")

    }

    override fun onBtnChangeLevelClick(position: Int) {

    }
}
