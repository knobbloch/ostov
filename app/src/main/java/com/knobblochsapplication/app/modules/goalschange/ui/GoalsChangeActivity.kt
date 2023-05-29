package com.knobblochsapplication.app.modules.goalschange.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.views.ImagePickerFragmentDialog
import com.knobblochsapplication.app.databinding.ActivityGoalsChangeBinding
import com.knobblochsapplication.app.modules.goalschange.`data`.viewmodel.GoalsChangeVM
import kotlin.String
import kotlin.Unit

class GoalsChangeActivity : BaseActivity<ActivityGoalsChangeBinding>(R.layout.activity_goals_change)
    {
  private val viewModel: GoalsChangeVM by viewModels<GoalsChangeVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.goalsChangeVM = viewModel
  }

  override fun setUpClicks(): Unit {


    binding.imageCamera.setOnClickListener {
      ImagePickerFragmentDialog().show(supportFragmentManager)
      { path ->//TODO HANDLE DATA
      }

    }
  }

  companion object {
    const val TAG: String = "GOALS_CHANGE_ACTIVITY"

  }
}
