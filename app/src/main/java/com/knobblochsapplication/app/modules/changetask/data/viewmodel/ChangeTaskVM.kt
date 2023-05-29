package com.knobblochsapplication.app.modules.changetask.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.changetask.`data`.model.ChangeTaskModel
import org.koin.core.KoinComponent

class ChangeTaskVM : ViewModel(), KoinComponent {
  val changeTaskModel: MutableLiveData<ChangeTaskModel> = MutableLiveData(ChangeTaskModel())

  var navArguments: Bundle? = null
}
