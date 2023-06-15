package com.knobblochsapplication.app.modules.addtask.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityAddTaskBinding
import com.knobblochsapplication.app.modules.addtask.data.viewmodel.AddTaskVM

class AddTaskActivity : BaseActivity<ActivityAddTaskBinding>(R.layout.activity_add_task) {
  private val viewModel: AddTaskVM by viewModels<AddTaskVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.addTaskVM = viewModel
  }

  override fun setUpClicks(): Unit {


    binding.imageCamera.setOnClickListener {
    }
  }

  companion object {
    const val TAG: String = "ADD_TASK_ACTIVITY"

  }
}
