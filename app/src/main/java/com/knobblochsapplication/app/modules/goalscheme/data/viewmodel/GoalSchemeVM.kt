package com.knobblochsapplication.app.modules.goalscheme.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.goalscheme.`data`.model.GoalSchemeModel
import org.koin.core.KoinComponent

class GoalSchemeVM : ViewModel(), KoinComponent {
  val goalSchemeModel: MutableLiveData<GoalSchemeModel> = MutableLiveData(GoalSchemeModel())

  var navArguments: Bundle? = null
}
