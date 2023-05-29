package com.knobblochsapplication.app.modules.goalsuniontwo.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalsUniontwoBinding
import com.knobblochsapplication.app.modules.goalsuniontwo.`data`.viewmodel.GoalsUniontwoVM
import kotlin.String
import kotlin.Unit

class GoalsUniontwoActivity :
    BaseActivity<ActivityGoalsUniontwoBinding>(R.layout.activity_goals_uniontwo) {
  private val viewModel: GoalsUniontwoVM by viewModels<GoalsUniontwoVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.goalsUniontwoVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "GOALS_UNIONTWO_ACTIVITY"

  }
}
