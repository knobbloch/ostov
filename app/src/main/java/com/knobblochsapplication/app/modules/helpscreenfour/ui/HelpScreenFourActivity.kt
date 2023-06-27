package com.knobblochsapplication.app.modules.helpscreenfour.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityHelpScreenFourBinding
import com.knobblochsapplication.app.modules.helpscreenfour.data.viewmodel.HelpScreenFourVM
import com.knobblochsapplication.app.modules.helpscreenhelpcards.HelpScreenHelpCardsActivity
import com.knobblochsapplication.app.modules.helpscreentwo.ui.HelpScreenTwoActivity
import org.koin.android.ext.android.inject

class HelpScreenFourActivity :
    BaseActivity<ActivityHelpScreenFourBinding>(R.layout.activity_help_screen_four) {
    private val viewModel: HelpScreenFourVM by viewModels<HelpScreenFourVM>()
    private val preferenceHelper: PreferenceHelper by inject()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        binding.helpScreenFourVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.floatingActionButton.setOnClickListener {
            this.finish()
            val myIntent = Intent(this, HelpScreenHelpCardsActivity::class.java)
            this.startActivity(myIntent)
        }
    }

    companion object {
        const val TAG: String = "HELP_SCREEN_FOUR_ACTIVITY"


    }
}
