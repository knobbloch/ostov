package com.knobblochsapplication.app.modules.menuunion.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.menuunion.`data`.model.MenuUnionModel
import org.koin.core.KoinComponent

class MenuUnionVM : ViewModel(), KoinComponent {
  val menuUnionModel: MutableLiveData<MenuUnionModel> = MutableLiveData(MenuUnionModel())

  var navArguments: Bundle? = null
}
