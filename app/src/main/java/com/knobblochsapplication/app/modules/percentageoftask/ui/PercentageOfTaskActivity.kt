package com.knobblochsapplication.app.modules.percentageoftask.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityPercentageOfTaskBinding
import com.knobblochsapplication.app.modules.percentageoftask.`data`.viewmodel.PercentageOfTaskVM
import kotlin.String
import kotlin.Unit

class PercentageOfTaskActivity :
    BaseActivity<ActivityPercentageOfTaskBinding>(R.layout.activity_percentage_of_task) {
  private val viewModel: PercentageOfTaskVM by viewModels<PercentageOfTaskVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.percentageOfTaskVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "PERCENTAGE_OF_TASK_ACTIVITY"

  }
}
