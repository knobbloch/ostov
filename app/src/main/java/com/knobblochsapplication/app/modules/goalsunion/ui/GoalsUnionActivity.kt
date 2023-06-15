package com.knobblochsapplication.app.modules.goalsunion.ui

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalsUnionBinding
import com.knobblochsapplication.app.modules.goals.ui.Goal
import com.knobblochsapplication.app.modules.goalsunion.data.viewmodel.GoalsUnionVM


class GoalsUnionActivity : BaseActivity<ActivityGoalsUnionBinding>(R.layout.activity_goals_union) {

    private val viewModel: GoalsUnionVM by viewModels<GoalsUnionVM>()
    private val goalsUnionList = ArrayList<Goal>()

    private val adapter = GoalsUnionAdapter(goalsUnionList)
    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.goalsUnionVM = viewModel
        binding = ActivityGoalsUnionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun setUpClicks(): Unit {

    }

    private fun init() {
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@GoalsUnionActivity)
            rcView.adapter = adapter

            frameContainer.setOnClickListener{
                finish()
            }
            btnArrowleft.setOnClickListener {
                Toast.makeText(this@GoalsUnionActivity, "переходим куда-то чтобы как-то склеить этих челов вместе", Toast.LENGTH_LONG).show()
            }
        }


    }

    companion object {
        const val TAG: String = "GOALS_UNION_ACTIVITY"

    }
}
