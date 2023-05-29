package com.knobblochsapplication.app.modules.goals.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.goals.`data`.model.GoalsModel
import org.koin.core.KoinComponent

class GoalsVM : ViewModel(), KoinComponent {
  val goalsModel: MutableLiveData<GoalsModel> = MutableLiveData(GoalsModel())

  var navArguments: Bundle? = null
}
