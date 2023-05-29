package com.knobblochsapplication.app.modules.menuone.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.views.ImagePickerFragmentDialog
import com.knobblochsapplication.app.databinding.ActivityMenuOneBinding
import com.knobblochsapplication.app.modules.menuone.`data`.viewmodel.MenuOneVM
import kotlin.Boolean
import kotlin.String
import kotlin.Unit

class MenuOneActivity : BaseActivity<ActivityMenuOneBinding>(R.layout.activity_menu_one) {
  private val viewModel: MenuOneVM by viewModels<MenuOneVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.menuOneVM = viewModel
    setUpSearchViewStatelayerListener()
  }

  override fun setUpClicks(): Unit {
    binding.imageCamera.setOnClickListener {
      ImagePickerFragmentDialog().show(supportFragmentManager)
      { path ->//TODO HANDLE DATA
      }

    }
  }

  private fun setUpSearchViewStatelayerListener(): Unit {
    binding.searchViewStatelayer.setOnQueryTextListener(object :
    SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(p0 : String) : Boolean {
        // Performs search when user hit
        // the search button on the keyboard
        return false
      }
      override fun onQueryTextChange(p0 : String) : Boolean {
        // Start filtering the list as user
        // start entering the characters
        return false
      }
      })
    }

    companion object {
      const val TAG: String = "MENU_ONE_ACTIVITY"


      fun getIntent(context: Context, bundle: Bundle?): Intent {
        val destIntent = Intent(context, MenuOneActivity::class.java)
        destIntent.putExtra("bundle", bundle)
        return destIntent
      }
    }
  }
