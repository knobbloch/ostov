package com.knobblochsapplication.app.modules.downloadlist.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.Docx.PermissionUtils
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.appcomponents.utility.SortType
import com.knobblochsapplication.app.databinding.ActivityDownloadListBinding
import com.knobblochsapplication.app.modules.downloadlist.data.viewmodel.DownloadListVM
import org.koin.android.ext.android.inject

class DownloadListActivity :
    BaseActivity<ActivityDownloadListBinding>(R.layout.activity_download_list) {
    private val viewModel: DownloadListVM by viewModels()
    private val appStorage: AppStorage by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        binding.downloadListVM = viewModel
    }

    override fun setUpClicks() {
        when (preferenceHelper.getSortType()) {
            SortType.BY_PRIORITY -> {
                binding.date.isChecked = false
                binding.priority.isChecked = true
                binding.completion.isChecked = false
                binding.priorityDate.isChecked = false
            }

            SortType.BY_DEADLINE -> {
                binding.date.isChecked = true
                binding.priority.isChecked = false
                binding.completion.isChecked = false
                binding.priorityDate.isChecked = false
            }

            SortType.BY_COMPLETION -> {
                binding.date.isChecked = false
                binding.priority.isChecked = false
                binding.completion.isChecked = true
                binding.priorityDate.isChecked = false
            }

            SortType.BY_PRIORITY_DEADLINE -> {
                binding.date.isChecked = false
                binding.priority.isChecked = false
                binding.completion.isChecked = false
                binding.priorityDate.isChecked = true
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        val uid = preferenceHelper.getLastSelectedGoal()

        binding.buttonDownload.setOnClickListener {
            when (binding.sort.checkedRadioButtonId) {
                R.id.priority -> {
                    appStorage.sortByPriority(uid!!)
                }

                R.id.completion -> {
                    appStorage.sortByCompletion(uid!!)
                }

                R.id.date -> {
                    appStorage.sortByDeadline(uid!!)
                }

                R.id.priority_date -> {
                    appStorage.sortByPriorityDeadline(uid!!)
                }
            }
            if (!PermissionUtils.hasPermissions(this)) {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else{
                appStorage.downloadDocxFile(uid!!)
                Toast.makeText(
                    this,
                    "Документ сохранен в папке " + Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    ).name,
                    Toast.LENGTH_LONG
                ).show()
                this.finish()
            }

        }
    }

    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val uid = preferenceHelper.getLastSelectedGoal()
                appStorage.downloadDocxFile(uid!!)
                Toast.makeText(
                    this,
                    "Документ сохранен в папке " + Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    ).name,
                    Toast.LENGTH_LONG
                ).show()

            } else {

            }
        }

    companion object {
        const val TAG: String = "DOWNLOAD_LIST_ACTIVITY"

    }
}
