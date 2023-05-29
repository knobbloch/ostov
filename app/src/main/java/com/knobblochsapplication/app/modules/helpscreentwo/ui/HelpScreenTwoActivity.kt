package com.knobblochsapplication.app.modules.helpscreentwo.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityHelpScreenTwoBinding
import com.knobblochsapplication.app.modules.helpscreenthree.ui.HelpScreenThreeActivity
import com.knobblochsapplication.app.modules.helpscreentwo.`data`.viewmodel.HelpScreenTwoVM
import com.knobblochsapplication.app.modules.menuone.ui.MenuOneActivity
import com.knobblochsapplication.app.modules.percentageofgoal.ui.PercentageOfGoalActivity
import kotlin.String
import kotlin.Unit

class HelpScreenTwoActivity :
    BaseActivity<ActivityHelpScreenTwoBinding>(R.layout.activity_help_screen_two) {
  private val viewModel: HelpScreenTwoVM by viewModels<HelpScreenTwoVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.helpScreenTwoVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.txtSeventyFive.setOnClickListener {
      val destIntent = PercentageOfGoalActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.imageMenu.setOnClickListener {
      val destIntent = MenuOneActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnArrowright.setOnClickListener {
      val destIntent = HelpScreenThreeActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "HELP_SCREEN_TWO_ACTIVITY"

  }
}
