package com.knobblochsapplication.app.modules.goalsdelete.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.goalsdelete.`data`.model.GoalsDeleteModel
import org.koin.core.KoinComponent

class GoalsDeleteVM : ViewModel(), KoinComponent {
  val goalsDeleteModel: MutableLiveData<GoalsDeleteModel> = MutableLiveData(GoalsDeleteModel())

  var navArguments: Bundle? = null
}
