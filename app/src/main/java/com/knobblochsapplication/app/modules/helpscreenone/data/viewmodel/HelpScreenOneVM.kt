package com.knobblochsapplication.app.modules.helpscreenone.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.helpscreenone.`data`.model.HelpScreenOneModel
import org.koin.core.KoinComponent

class HelpScreenOneVM : ViewModel(), KoinComponent {
  val helpScreenOneModel: MutableLiveData<HelpScreenOneModel> =
      MutableLiveData(HelpScreenOneModel())

  var navArguments: Bundle? = null
}
