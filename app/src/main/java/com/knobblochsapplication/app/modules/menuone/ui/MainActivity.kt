package com.knobblochsapplication.app.modules.menuone.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityMainMenuBinding
import com.knobblochsapplication.app.modules.diagramview.ui.DiagramViewActivity
import com.knobblochsapplication.app.modules.downloadlist.ui.DownloadListActivity
import com.knobblochsapplication.app.modules.goals.ui.CreateGoalDialogFragment
import com.knobblochsapplication.app.modules.goals.ui.GoalsActivity
import com.knobblochsapplication.app.modules.helpscreenone.ui.HelpScreenOneActivity
import com.knobblochsapplication.app.modules.settings.ui.SettingsActivity
import com.knobblochsapplication.app.modules.sort.ui.SortActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainMenuBinding
    private val preferenceHelper: PreferenceHelper by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
//        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)

        if (savedInstanceState == null) {
            if (!preferenceHelper.isHelpPageShowed()) {
                val myIntent = Intent(this, HelpScreenOneActivity::class.java)
                this.startActivity(myIntent)
            }

            if (preferenceHelper.isDarkTheme()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.list -> {
                    val myIntent = Intent(this@MainActivity, GoalsActivity::class.java)
                    startActivity(myIntent)
                    true
                }

                else -> false
            }
        }
        binding.include.addGoalBtn.setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setNeutralButton(R.string.lbl21) { dialog, _ ->
                    dialog.dismiss()
                }
                .setItems(R.array.dialog_actions_with_task) { dialog, which ->
                    when (which) {
                        0 -> {
                            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                            ft.addToBackStack(null)
                            val newFragment: DialogFragment = CreateTaskDialogFragment.newInstance()
                            newFragment.show(ft, "dialog")

                            dialog.dismiss()
                        }

                        1 -> {
                            dialog.dismiss()
                        }

                        2 -> {
                            MaterialAlertDialogBuilder(this)
                                .setNegativeButton(R.string.lbl21) { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .setPositiveButton(R.string.lbl22) { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .setSingleChoiceItems(
                                    R.array.dialog_delete_task,
                                    0
                                ) { dialog, which ->
                                    when (which) {
                                        0 -> {

                                        }

                                        1 -> {

                                        }
                                    }
                                }
                                .setTitle(R.string.lbl34)
                                .show()
                            dialog.dismiss()

                        }

                        3 -> {
                            dialog.dismiss()

                        }
                    }
                }
                .setTitle(R.string.change_action)
                .create()
                .show()
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