package com.knobblochsapplication.app.modules.addtask.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.addtask.`data`.model.AddTaskModel
import org.koin.core.KoinComponent

class AddTaskVM : ViewModel(), KoinComponent {
  val addTaskModel: MutableLiveData<AddTaskModel> = MutableLiveData(AddTaskModel())

  var navArguments: Bundle? = null
}
