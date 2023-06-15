package com.knobblochsapplication.app.modules.helpscreenone.ui

import android.content.Intent
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityHelpScreenOneBinding
import com.knobblochsapplication.app.modules.helpscreenone.data.viewmodel.HelpScreenOneVM
import com.knobblochsapplication.app.modules.helpscreentwo.ui.HelpScreenTwoActivity

class HelpScreenOneActivity :
    BaseActivity<ActivityHelpScreenOneBinding>(R.layout.activity_help_screen_one) {
    private val viewModel: HelpScreenOneVM by viewModels<HelpScreenOneVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        binding.helpScreenOneVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.btnStart.setOnClickListener {
            this.finish()
            val myIntent = Intent(this, HelpScreenTwoActivity::class.java)
            this.startActivity(myIntent)
        }
    }

    companion object {
        const val TAG: String = "HELP_SCREEN_ONE_ACTIVITY"

    }
}
