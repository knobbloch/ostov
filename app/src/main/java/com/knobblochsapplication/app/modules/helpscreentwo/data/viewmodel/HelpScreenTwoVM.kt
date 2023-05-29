package com.knobblochsapplication.app.modules.helpscreentwo.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.helpscreentwo.`data`.model.HelpScreenTwoModel
import org.koin.core.KoinComponent

class HelpScreenTwoVM : ViewModel(), KoinComponent {
  val helpScreenTwoModel: MutableLiveData<HelpScreenTwoModel> =
      MutableLiveData(HelpScreenTwoModel())

  var navArguments: Bundle? = null
}
