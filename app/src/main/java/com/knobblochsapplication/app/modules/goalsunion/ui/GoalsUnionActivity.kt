package com.knobblochsapplication.app.modules.goalsunion.ui

import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityGoalsUnionBinding
import com.knobblochsapplication.app.modules.goalsunion.data.viewmodel.GoalsUnionVM
import org.koin.android.ext.android.inject


class GoalsUnionActivity : BaseActivity<ActivityGoalsUnionBinding>(R.layout.activity_goals_union),
    GoalsUnionAdapter.Listener {
    private val viewModel: GoalsUnionVM by viewModels()
    private val appStorage: AppStorage by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    lateinit var adapter: GoalsUnionAdapter
    private var selectedGoalUid: String? = null
    private lateinit var selectedTaskUid: String

    override fun addObservers() {
        val uid = preferenceHelper.getLastSelectedGoal()
        if (uid != null) {
            selectedTaskUid = uid
            adapter = GoalsUnionAdapter(this, appStorage.filter(uid))
        } else {
            adapter = GoalsUnionAdapter(this, mutableListOf())
        }
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.goalsUnionVM = viewModel
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        binding.rcView.layoutManager = LinearLayoutManager(this@GoalsUnionActivity)
        binding.rcView.adapter = adapter
    }

    override fun setUpClicks() {
        binding.topAppBar1.setNavigationOnClickListener {
            this.finish()
        }
        binding.btnNext.setOnClickListener {
            if (selectedGoalUid == null) {
                Toast.makeText(this@GoalsUnionActivity, R.string.toast_select_goal, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            appStorage.union(selectedGoalUid!!, selectedTaskUid)
            this.finish()
        }
    }

    override fun onInitialized() {

    }

    override fun onBtnRadioClick(position: Int, uid: String) {
        this.selectedGoalUid = uid
    }

    companion object {
        const val TAG: String = "GOALS_UNION_ACTIVITY"
    }
}
