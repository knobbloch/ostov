package com.knobblochsapplication.app.modules.main.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityGoalsBinding
import com.knobblochsapplication.app.modules.diagramview.ui.DiagramViewActivity
import com.knobblochsapplication.app.modules.downloadlist.ui.DownloadListActivity
import com.knobblochsapplication.app.modules.goal.ui.GoalActivity
import com.knobblochsapplication.app.modules.goalsunion.ui.GoalsUnionActivity
import com.knobblochsapplication.app.modules.helpscreenone.ui.HelpScreenOneActivity
import com.knobblochsapplication.app.modules.settings.ui.SettingsActivity
import com.knobblochsapplication.app.modules.sort.ui.SortActivity
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(),
    GoalsAdapter.Listener {
    lateinit var binding: ActivityGoalsBinding
    private val appStorage: AppStorage by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    lateinit var adapter: GoalsAdapter

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
//        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)

        adapter = GoalsAdapter(this, appStorage.goals)

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)
        }

        binding.addGoalBtn.setOnClickListener {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.addToBackStack(null)
            val newFragment: DialogFragment = CreateGoalDialogFragment.newInstance()
            newFragment.show(ft, "dialog")
        }
        binding.rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rcView.adapter = adapter

        if (android.os.Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }


    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val TAG: String = "GOALS_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, MainActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
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

    override fun onGoalClick(position: Int) {
        preferenceHelper.setLastSelectedGoal(appStorage.goals[position].uid)
        val myIntent = Intent(this, GoalActivity::class.java)
        this.startActivity(myIntent)
    }

    override fun onBtnDeleteClick(position: Int, uid: String) {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.msg6))
            .setNegativeButton(R.string.lbl21) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl22) { dialog, _ ->
                appStorage.deleteGoal(uid)
                adapter.notifyItemRemoved(position)
                preferenceHelper.setLastSelectedGoal(null)
                dialog.dismiss()
            }
            .show()
    }

    override fun onBtnEditClick(position: Int) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        val newFragment: DialogFragment = EditGoalDialogFragment.newInstance(position)
        newFragment.show(ft, "dialog")

    }

    override fun onBtnChangeLevelClick(position: Int) {
        if (appStorage.goals.count() == 1) {
            Toast.makeText(this@MainActivity, R.string.toast_union_goal, Toast.LENGTH_LONG).show()
            return
        }
        preferenceHelper.setLastSelectedGoal(appStorage.goals[position].uid)
        val myIntent = Intent(this, GoalsUnionActivity::class.java)
        this.startActivity(myIntent)
    }
}
