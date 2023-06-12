package com.knobblochsapplication.app.modules.helpscreenone.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityHelpScreenOneBinding
import com.knobblochsapplication.app.modules.helpscreenone.`data`.viewmodel.HelpScreenOneVM
import kotlin.String
import kotlin.Unit

class HelpScreenOneActivity :
  BaseActivity<ActivityHelpScreenOneBinding>(R.layout.activity_help_screen_one) {
  private val viewModel: HelpScreenOneVM by viewModels<HelpScreenOneVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.helpScreenOneVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "HELP_SCREEN_ONE_ACTIVITY"

  }
}
