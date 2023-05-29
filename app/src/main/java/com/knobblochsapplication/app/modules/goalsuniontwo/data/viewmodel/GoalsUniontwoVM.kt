package com.knobblochsapplication.app.modules.goalsuniontwo.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.goalsuniontwo.`data`.model.GoalsUniontwoModel
import org.koin.core.KoinComponent

class GoalsUniontwoVM : ViewModel(), KoinComponent {
  val goalsUniontwoModel: MutableLiveData<GoalsUniontwoModel> =
      MutableLiveData(GoalsUniontwoModel())

  var navArguments: Bundle? = null
}
