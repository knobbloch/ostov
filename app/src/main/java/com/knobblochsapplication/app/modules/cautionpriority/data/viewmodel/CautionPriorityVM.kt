package com.knobblochsapplication.app.modules.cautionpriority.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.cautionpriority.`data`.model.CautionPriorityModel
import org.koin.core.KoinComponent

class CautionPriorityVM : ViewModel(), KoinComponent {
  val cautionPriorityModel: MutableLiveData<CautionPriorityModel> =
      MutableLiveData(CautionPriorityModel())

  var navArguments: Bundle? = null
}
