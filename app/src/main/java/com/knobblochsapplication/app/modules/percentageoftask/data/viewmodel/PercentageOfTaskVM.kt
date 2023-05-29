package com.knobblochsapplication.app.modules.percentageoftask.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.percentageoftask.`data`.model.PercentageOfTaskModel
import org.koin.core.KoinComponent

class PercentageOfTaskVM : ViewModel(), KoinComponent {
  val percentageOfTaskModel: MutableLiveData<PercentageOfTaskModel> =
      MutableLiveData(PercentageOfTaskModel())

  var navArguments: Bundle? = null
}
