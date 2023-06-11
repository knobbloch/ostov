package com.knobblochsapplication.app.modules.helpscreenthree.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityHelpScreenThreeBinding
import com.knobblochsapplication.app.modules.helpscreenfour.ui.HelpScreenFourActivity
import com.knobblochsapplication.app.modules.helpscreenthree.data.viewmodel.HelpScreenThreeVM

class HelpScreenThreeActivity :
    BaseActivity<ActivityHelpScreenThreeBinding>(R.layout.activity_help_screen_three) {
  private val viewModel: HelpScreenThreeVM by viewModels<HelpScreenThreeVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
    binding.helpScreenThreeVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.floatingActionButton.setOnClickListener {
      this.finish()
      val myIntent = Intent(this, HelpScreenFourActivity::class.java)
      this.startActivity(myIntent)
    }
  }

  companion object {
    const val TAG: String = "HELP_SCREEN_THREE_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, HelpScreenThreeActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
