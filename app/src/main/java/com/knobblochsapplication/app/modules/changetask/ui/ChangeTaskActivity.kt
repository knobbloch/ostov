package com.knobblochsapplication.app.modules.changetask.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.views.ImagePickerFragmentDialog
import com.knobblochsapplication.app.databinding.ActivityChangeTaskBinding
import com.knobblochsapplication.app.modules.cautionpriority.ui.CautionPriorityActivity
import com.knobblochsapplication.app.modules.changetask.`data`.viewmodel.ChangeTaskVM
import kotlin.String
import kotlin.Unit

class ChangeTaskActivity : BaseActivity<ActivityChangeTaskBinding>(R.layout.activity_change_task) {
  private val viewModel: ChangeTaskVM by viewModels<ChangeTaskVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.changeTaskVM = viewModel
  }

  override fun setUpClicks(): Unit {


    binding.imageCamera.setOnClickListener {
      ImagePickerFragmentDialog().show(supportFragmentManager)
      { path ->//TODO HANDLE DATA
      }

    }
    binding.btnVectorOne.setOnClickListener {
      val destIntent = CautionPriorityActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "CHANGE_TASK_ACTIVITY"

  }
}
