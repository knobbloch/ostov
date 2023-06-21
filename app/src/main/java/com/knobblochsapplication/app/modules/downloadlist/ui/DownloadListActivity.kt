package com.knobblochsapplication.app.modules.downloadlist.ui

import android.os.Environment
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
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
//    val downloadListAdapter =
//    DownloadListAdapter(viewModel.downloadListList.value?:mutableListOf())
//    binding.recyclerDownloadList.adapter = downloadListAdapter
//    downloadListAdapter.setOnItemClickListener(
//    object : DownloadListAdapter.OnItemClickListener {
//      override fun onItemClick(view:View, position:Int, item : DownloadListRowModel) {
//        onClickRecyclerDownloadList(view, position, item)
//      }
//    }
//    )
    viewModel.downloadListList.observe(this) {
//      downloadListAdapter.updateData(it)
    }
    binding.downloadListVM = viewModel
  }

  override fun setUpClicks() {
    binding.topAppBar.setNavigationOnClickListener {
      finish()
    }
    val uid = preferenceHelper.getLastSelectedGoal()
    binding.buttonDownload.setOnClickListener {
        appStorage.downloadDocxFile(uid!!)
        Toast.makeText(
            this,
            "Документ был успешно создан в папке " + Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ).name,
            Toast.LENGTH_LONG
        ).show()
        this.finish()
    }
  }


  companion object {
    const val TAG: String = "DOWNLOAD_LIST_ACTIVITY"

  }
}
