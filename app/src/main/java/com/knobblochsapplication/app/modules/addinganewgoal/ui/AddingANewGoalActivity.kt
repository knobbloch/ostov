package com.knobblochsapplication.app.modules.addinganewgoal.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.views.ImagePickerFragmentDialog
import com.knobblochsapplication.app.databinding.ActivityAddingANewGoalBinding
import com.knobblochsapplication.app.modules.goals.ui.GoalsActivity
import com.knobblochsapplication.app.modules.menuunion.ui.MenuUnionActivity
import kotlin.String
import kotlin.Unit

class AddingANewGoalActivity :
    BaseActivity<ActivityAddingANewGoalBinding>(R.layout.activity_adding_a_new_goal) {
  //private val viewModel: AddingANewGoalVM by viewModels<AddingANewGoalVM>()

  override fun onInitialized(): Unit {
    //viewModel.navArguments = intent.extras?.getBundle("bundle")
      //binding.addingANewGoalVM = viewModel
  }

  override fun setUpClicks(): Unit {

  }



  companion object {
    const val TAG: String = "ADDING_A_NEW_GOAL_ACTIVITY"

  }
}
