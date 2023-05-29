package com.knobblochsapplication.app.modules.menuunion.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityMenuUnionBinding
import com.knobblochsapplication.app.modules.menuunion.`data`.viewmodel.MenuUnionVM
import kotlin.String
import kotlin.Unit

class MenuUnionActivity : BaseActivity<ActivityMenuUnionBinding>(R.layout.activity_menu_union) {
  private val viewModel: MenuUnionVM by viewModels<MenuUnionVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.menuUnionVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "MENU_UNION_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, MenuUnionActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
