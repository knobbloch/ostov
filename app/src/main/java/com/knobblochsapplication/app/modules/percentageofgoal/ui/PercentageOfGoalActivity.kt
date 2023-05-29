package com.knobblochsapplication.app.modules.percentageofgoal.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityPercentageOfGoalBinding
import com.knobblochsapplication.app.modules.percentageofgoal.`data`.viewmodel.PercentageOfGoalVM
import kotlin.String
import kotlin.Unit

class PercentageOfGoalActivity :
    BaseActivity<ActivityPercentageOfGoalBinding>(R.layout.activity_percentage_of_goal) {
  private val viewModel: PercentageOfGoalVM by viewModels<PercentageOfGoalVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.percentageOfGoalVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "PERCENTAGE_OF_GOAL_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, PercentageOfGoalActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
