package com.knobblochsapplication.app.modules.menuone.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityMainMenuBinding
import com.knobblochsapplication.app.modules.diagramview.ui.DiagramViewActivity
import com.knobblochsapplication.app.modules.downloadlist.ui.DownloadListActivity
import com.knobblochsapplication.app.modules.settings.ui.SettingsActivity
import com.knobblochsapplication.app.modules.sort.ui.SortActivity
import org.koin.android.ext.android.inject

class MainMenu : AppCompatActivity() {
    lateinit var binding: ActivityMainMenuBinding
    private val preferenceHelper: PreferenceHelper by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
//        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                drawer.openDrawer(GravityCompat.START)
            }
        }

        if (preferenceHelper.isDarkTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun onClickGoSettings(item: MenuItem) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoDiagramView(item: MenuItem) {
        val intent = Intent(this, DiagramViewActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoSortType(item: MenuItem) {
        val intent = Intent(this, SortActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoDownloadList(item: MenuItem) {
        val intent = Intent(this, DownloadListActivity::class.java)
        startActivity(intent)
    }

}