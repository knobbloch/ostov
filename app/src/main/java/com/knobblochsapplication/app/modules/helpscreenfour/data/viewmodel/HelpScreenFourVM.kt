package com.knobblochsapplication.app.modules.helpscreenfour.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.helpscreenfour.`data`.model.HelpScreenFourModel
import org.koin.core.KoinComponent

class HelpScreenFourVM : ViewModel(), KoinComponent {
  val helpScreenFourModel: MutableLiveData<HelpScreenFourModel> =
      MutableLiveData(HelpScreenFourModel())

  var navArguments: Bundle? = null
}
