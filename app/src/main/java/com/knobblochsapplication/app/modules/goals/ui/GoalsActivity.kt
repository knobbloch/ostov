package com.knobblochsapplication.app.modules.goals.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalsBinding
import com.knobblochsapplication.app.modules.goals.`data`.viewmodel.GoalsVM
import com.knobblochsapplication.app.modules.menuunion.ui.MenuUnionActivity
import kotlin.String
import kotlin.Unit

class GoalsActivity : BaseActivity<ActivityGoalsBinding>(R.layout.activity_goals) {
  private val viewModel: GoalsVM by viewModels<GoalsVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.goalsVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.linearBlockgoal1.setOnClickListener {
      val destIntent = MenuUnionActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  companion object {
    const val TAG: String = "GOALS_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, GoalsActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
