package com.knobblochsapplication.app.modules.goalschange.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.goalschange.`data`.model.GoalsChangeModel
import org.koin.core.KoinComponent

class GoalsChangeVM : ViewModel(), KoinComponent {
  val goalsChangeModel: MutableLiveData<GoalsChangeModel> = MutableLiveData(GoalsChangeModel())

  var navArguments: Bundle? = null
}