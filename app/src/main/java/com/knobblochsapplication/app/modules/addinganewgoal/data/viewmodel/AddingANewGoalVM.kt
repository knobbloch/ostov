package com.knobblochsapplication.app.modules.addinganewgoal.data.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.sort.addinganewgoal.data.model.AddingANewGoalModel
import org.koin.core.KoinComponent

class AddingANewGoalVM : ViewModel(), KoinComponent {
  val addingANewGoalModel: MutableLiveData<AddingANewGoalModel> =
      MutableLiveData(AddingANewGoalModel())

  var navArguments: Bundle? = null
}
