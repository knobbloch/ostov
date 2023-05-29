package com.knobblochsapplication.app.modules.diagramview.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.diagramview.`data`.model.DiagramViewModel
import org.koin.core.KoinComponent

class DiagramViewVM : ViewModel(), KoinComponent {
  val diagramViewModel: MutableLiveData<DiagramViewModel> = MutableLiveData(DiagramViewModel())

  var navArguments: Bundle? = null
}
