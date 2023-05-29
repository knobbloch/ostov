package com.knobblochsapplication.app.modules.menutwo.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityMenuTwoBinding
import com.knobblochsapplication.app.modules.menutwo.`data`.viewmodel.MenuTwoVM
import kotlin.String
import kotlin.Unit

class MenuTwoActivity : BaseActivity<ActivityMenuTwoBinding>(R.layout.activity_menu_two) {
  private val viewModel: MenuTwoVM by viewModels<MenuTwoVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.menuTwoVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "MENU_TWO_ACTIVITY"

  }
}
