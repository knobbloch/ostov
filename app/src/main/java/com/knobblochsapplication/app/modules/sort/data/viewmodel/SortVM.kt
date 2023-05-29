package com.knobblochsapplication.app.modules.sort.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.sort.`data`.model.SortModel
import com.knobblochsapplication.app.modules.sort.`data`.model.SortRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class SortVM : ViewModel(), KoinComponent {
  val sortModel: MutableLiveData<SortModel> = MutableLiveData(SortModel())

  var navArguments: Bundle? = null

  val sortList: MutableLiveData<MutableList<SortRowModel>> = MutableLiveData(mutableListOf())
}
