package com.knobblochsapplication.app.modules.settings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivitySettingsBinding
import com.knobblochsapplication.app.modules.settings.data.viewmodel.SettingsVM
import org.koin.android.ext.android.inject

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    private val viewModel: SettingsVM by viewModels<SettingsVM>()
    private val preferenceHelper: PreferenceHelper by inject()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
//      window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)
        binding.settingsVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.switchDarkTheme.isChecked = preferenceHelper.isDarkTheme()
        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            preferenceHelper.setDarkTheme(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        binding.notifications.isChecked = preferenceHelper.getIsNotificationEnabled()
        binding.notifications.setOnCheckedChangeListener { _, isChecked ->
            preferenceHelper.setIsNotificationEnabled(isChecked)
        }
    }

    companion object {
        const val TAG: String = "SETTINGS_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, SettingsActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}
