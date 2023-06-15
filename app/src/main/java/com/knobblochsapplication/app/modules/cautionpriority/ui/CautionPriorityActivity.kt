package com.knobblochsapplication.app.modules.cautionpriority.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityCautionPriorityBinding
import com.knobblochsapplication.app.modules.cautionpriority.`data`.viewmodel.CautionPriorityVM
import kotlin.String
import kotlin.Unit

class CautionPriorityActivity :
    BaseActivity<ActivityCautionPriorityBinding>(R.layout.activity_caution_priority) {
  //private val viewModel: CautionPriorityVM by viewModels<CautionPriorityVM>()

  override fun onInitialized(): Unit {
    //viewModel.navArguments = intent.extras?.getBundle("bundle")
    //binding.cautionPriorityVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "CAUTION_PRIORITY_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, CautionPriorityActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}