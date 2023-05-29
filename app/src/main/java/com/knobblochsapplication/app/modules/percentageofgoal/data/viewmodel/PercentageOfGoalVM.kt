package com.knobblochsapplication.app.modules.percentageofgoal.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.percentageofgoal.`data`.model.PercentageOfGoalModel
import org.koin.core.KoinComponent

class PercentageOfGoalVM : ViewModel(), KoinComponent {
  val percentageOfGoalModel: MutableLiveData<PercentageOfGoalModel> =
      MutableLiveData(PercentageOfGoalModel())

  var navArguments: Bundle? = null
}
