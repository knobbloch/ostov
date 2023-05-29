package com.knobblochsapplication.app.modules.menu.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.menu.`data`.model.MenuModel
import org.koin.core.KoinComponent

class MenuVM : ViewModel(), KoinComponent {
  val menuModel: MutableLiveData<MenuModel> = MutableLiveData(MenuModel())

  var navArguments: Bundle? = null
}
