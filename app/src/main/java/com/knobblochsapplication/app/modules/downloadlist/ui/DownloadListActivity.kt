package com.knobblochsapplication.app.modules.downloadlist.ui

import android.view.View
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityDownloadListBinding
import com.knobblochsapplication.app.modules.downloadlist.`data`.model.DownloadListRowModel
import com.knobblochsapplication.app.modules.downloadlist.`data`.viewmodel.DownloadListVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class DownloadListActivity :
    BaseActivity<ActivityDownloadListBinding>(R.layout.activity_download_list) {
  private val viewModel: DownloadListVM by viewModels<DownloadListVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
    val downloadListAdapter =
    DownloadListAdapter(viewModel.downloadListList.value?:mutableListOf())
//    binding.recyclerDownloadList.adapter = downloadListAdapter
//    downloadListAdapter.setOnItemClickListener(
//    object : DownloadListAdapter.OnItemClickListener {
//      override fun onItemClick(view:View, position:Int, item : DownloadListRowModel) {
//        onClickRecyclerDownloadList(view, position, item)
//      }
//    }
//    )
    viewModel.downloadListList.observe(this) {
      downloadListAdapter.updateData(it)
    }
    binding.downloadListVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.topAppBar.setNavigationOnClickListener {
      finish()
    }
  }

  fun onClickRecyclerDownloadList(
    view: View,
    position: Int,
    item: DownloadListRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "DOWNLOAD_LIST_ACTIVITY"

  }
}
