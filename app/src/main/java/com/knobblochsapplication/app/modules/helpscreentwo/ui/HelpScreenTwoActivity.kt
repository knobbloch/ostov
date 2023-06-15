package com.knobblochsapplication.app.modules.helpscreentwo.ui

import android.content.Intent
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityHelpScreenTwoBinding
import com.knobblochsapplication.app.modules.helpscreenthree.ui.HelpScreenThreeActivity
import com.knobblochsapplication.app.modules.helpscreentwo.data.viewmodel.HelpScreenTwoVM

class HelpScreenTwoActivity :
    BaseActivity<ActivityHelpScreenTwoBinding>(R.layout.activity_help_screen_two) {
  private val viewModel: HelpScreenTwoVM by viewModels<HelpScreenTwoVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
    binding.helpScreenTwoVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.floatingActionButton.setOnClickListener {
      this.finish()
      val myIntent = Intent(this, HelpScreenThreeActivity::class.java)
      this.startActivity(myIntent)
    }
  }

  companion object {
    const val TAG: String = "HELP_SCREEN_TWO_ACTIVITY"

  }
}
