package com.knobblochsapplication.app.modules.helpscreenfour.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityHelpScreenFourBinding
import com.knobblochsapplication.app.modules.helpscreenfour.`data`.viewmodel.HelpScreenFourVM
import com.knobblochsapplication.app.modules.settings.ui.SettingsActivity
import kotlin.String
import kotlin.Unit

class HelpScreenFourActivity :
    BaseActivity<ActivityHelpScreenFourBinding>(R.layout.activity_help_screen_four) {
  private val viewModel: HelpScreenFourVM by viewModels<HelpScreenFourVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.helpScreenFourVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.txtAboutsettings.setOnClickListener {
      val destIntent = SettingsActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.btnArrowright.setOnClickListener {
      val destIntent = GoalSchemeActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "HELP_SCREEN_FOUR_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, HelpScreenFourActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
