package com.knobblochsapplication.app.modules.goalsdelete.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalsDeleteBinding
import com.knobblochsapplication.app.modules.goals.ui.GoalsActivity
import com.knobblochsapplication.app.modules.goalsdelete.`data`.viewmodel.GoalsDeleteVM
import kotlin.String
import kotlin.Unit

class GoalsDeleteActivity : BaseActivity<ActivityGoalsDeleteBinding>(R.layout.activity_goals_delete)
    {
  private val viewModel: GoalsDeleteVM by viewModels<GoalsDeleteVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.goalsDeleteVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btn.setOnClickListener {
      val destIntent = GoalsActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btn1.setOnClickListener {
      val destIntent = GoalsActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "GOALS_DELETE_ACTIVITY"

  }
}
