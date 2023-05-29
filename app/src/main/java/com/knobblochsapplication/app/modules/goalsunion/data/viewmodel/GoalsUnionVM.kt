package com.knobblochsapplication.app.modules.goalsunion.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.goalsunion.`data`.model.GoalsUnionModel
import com.knobblochsapplication.app.modules.goalsunion.`data`.model.GoalsUnionRowModel
import kotlin.collections.MutableList
import org.koin.core.KoinComponent

class GoalsUnionVM : ViewModel(), KoinComponent {
  val goalsUnionModel: MutableLiveData<GoalsUnionModel> = MutableLiveData(GoalsUnionModel())

  var navArguments: Bundle? = null

  val goalsUnionList: MutableLiveData<MutableList<GoalsUnionRowModel>> =
      MutableLiveData(mutableListOf())
}
