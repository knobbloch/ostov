package com.knobblochsapplication.app.modules.goalsunion.ui

import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityGoalsUnionBinding
import com.knobblochsapplication.app.modules.File_system.File_Manager
import com.knobblochsapplication.app.modules.File_system.Goal
import com.knobblochsapplication.app.modules.goalsunion.data.viewmodel.GoalsUnionVM
import org.koin.android.ext.android.inject
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.modules.goals.ui.GoalsAdapter


class GoalsUnionActivity : BaseActivity<ActivityGoalsUnionBinding>(R.layout.activity_goals_union),
    GoalsUnionAdapter.Listener {
    private val preferenceHelper: PreferenceHelper by inject()
    private var selectedGoalUid: Int = 0

    private val viewModel: GoalsUnionVM by viewModels<GoalsUnionVM>()
    private val goalsUnionList = ArrayList<Goal>()

    private var adapter = GoalsUnionAdapter(this, goalsUnionList)

    private val prevId = intent.getIntExtra("prev", 0)

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.goalsUnionVM = viewModel
        setContentView(binding.root)
    }

    override fun setUpClicks(): Unit {
        //binding.topAppBar1.setNavigationOnClickListener {
        //    this.finish()
       // }
        binding.btnArrowleft.setOnClickListener {
            if (selectedGoalUid == null) {
                Toast.makeText(this@GoalsUnionActivity, R.string.toast_select_goal, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            File_Manager.Connect_goals(prevId, selectedGoalUid, prevId)
            this.finish()
        }
    }


    companion object {
        const val TAG: String = "GOALS_UNION_ACTIVITY"

    }

    override fun addObservers() {
        val uid = preferenceHelper.getLastSelectedGoal()
        adapter = if (uid != 0)
            GoalsUnionAdapter(this, File_Manager.filter(uid))
        else
            GoalsUnionAdapter(this, ArrayList())

        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.goalsUnionVM = viewModel
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        binding.rcView.layoutManager = LinearLayoutManager(this@GoalsUnionActivity)
        binding.rcView.adapter = adapter
    }

    override fun onButtonRadioClick(position: Int, uid: Int) {
        this.selectedGoalUid = uid
    }

}
