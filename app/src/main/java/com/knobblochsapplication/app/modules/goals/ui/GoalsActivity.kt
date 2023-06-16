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
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityGoalsBinding
import com.knobblochsapplication.app.modules.goals.data.viewmodel.GoalsVM
import com.knobblochsapplication.app.modules.goalsunion.ui.GoalsUnionActivity
import org.koin.android.ext.android.inject


class GoalsActivity : BaseActivity<ActivityGoalsBinding>(R.layout.activity_goals),
    GoalsAdapter.Listener {
    private val viewModel: GoalsVM by viewModels<GoalsVM>()
    private val appStorage: AppStorage by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    lateinit var adapter:GoalsAdapter

    override fun addObservers(): Unit {
        adapter = GoalsAdapter(this, appStorage.goals)
    }

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.goalsVM = viewModel
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
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
        preferenceHelper.setLastSelectedGoal(appStorage.goals[position].uid)
        this.finish()
    }

    override fun onBtnDeleteClick(position: Int, uid: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.msg6))
            .setNegativeButton(R.string.lbl21) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl22) { dialog, _ ->
                appStorage.remove(uid)
                adapter.notifyItemRemoved(position)
                preferenceHelper.setLastSelectedGoal(null)
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
        if (appStorage.goals.count() == 1) {
            Toast.makeText(this@GoalsActivity, R.string.toast_union_goal, Toast.LENGTH_LONG).show()
            return
        }
        preferenceHelper.setLastSelectedGoal(appStorage.goals[position].uid)
        val myIntent = Intent(this, GoalsUnionActivity::class.java)
        this.startActivity(myIntent)
    }
}
