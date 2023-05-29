package com.knobblochsapplication.app.modules.downloadlist.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.downloadlist.`data`.model.DownloadListModel
import com.knobblochsapplication.app.modules.downloadlist.`data`.model.DownloadListRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class DownloadListVM : ViewModel(), KoinComponent {
  val downloadListModel: MutableLiveData<DownloadListModel> = MutableLiveData(DownloadListModel())

  var navArguments: Bundle? = null

  val downloadListList: MutableLiveData<MutableList<DownloadListRowModel>> =
      MutableLiveData(mutableListOf())
}
