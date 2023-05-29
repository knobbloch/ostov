package com.knobblochsapplication.app.modules.helpscreenthree.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.helpscreenthree.`data`.model.HelpScreenThreeModel
import org.koin.core.KoinComponent

class HelpScreenThreeVM : ViewModel(), KoinComponent {
  val helpScreenThreeModel: MutableLiveData<HelpScreenThreeModel> =
      MutableLiveData(HelpScreenThreeModel())

  var navArguments: Bundle? = null
}
