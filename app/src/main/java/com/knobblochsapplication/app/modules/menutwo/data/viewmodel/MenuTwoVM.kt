package com.knobblochsapplication.app.modules.menutwo.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knobblochsapplication.app.modules.menutwo.`data`.model.MenuTwoModel
import org.koin.core.KoinComponent

class MenuTwoVM : ViewModel(), KoinComponent {
  val menuTwoModel: MutableLiveData<MenuTwoModel> = MutableLiveData(MenuTwoModel())

  var navArguments: Bundle? = null
}
