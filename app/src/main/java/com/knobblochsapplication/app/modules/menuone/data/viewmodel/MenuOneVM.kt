package com.knobblochsapplication.app.modules.menuone.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.menuone.`data`.model.MenuOneModel
import org.koin.core.KoinComponent

class MenuOneVM : ViewModel(), KoinComponent {
  val menuOneModel: MutableLiveData<MenuOneModel> = MutableLiveData(MenuOneModel())

  var navArguments: Bundle? = null
}
