package com.knobblochsapplication.app.modules.helpscreenhelpcards

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityHelpCards2Binding
import com.knobblochsapplication.app.databinding.ActivityHelpScreenHelpCardsBinding
import com.knobblochsapplication.app.databinding.ActivityHelpScreenTwoBinding
import com.knobblochsapplication.app.modules.helpscreenfour.ui.HelpScreenFourActivity
import com.knobblochsapplication.app.modules.helpscreenthree.ui.HelpScreenThreeActivity
import com.knobblochsapplication.app.modules.helpscreentwo.data.viewmodel.HelpScreenTwoVM
import org.koin.android.ext.android.inject

class HelpScreenHelpCardsActivity :
    BaseActivity<ActivityHelpScreenHelpCardsBinding>(R.layout.activity_help_screen_help_cards) {
    //    private val viewModel: HelpCardsVM by viewModels()
    private val preferenceHelper: PreferenceHelper by inject()


    override fun onInitialized(): Unit {
//        viewModel.navArguments = intent.extras?.getBundle("bundle")
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
//        binding.diagramViewVM = viewModel
        preferenceHelper.setHelpPageShowed(true)

    }

    override fun setUpClicks(): Unit {
        binding.btnNext.setOnClickListener {
            this.finish()

        }

    }

    companion object {
        const val TAG: String = "HELP_SCREEN_HELP_CARDS_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, HelpScreenFourActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}