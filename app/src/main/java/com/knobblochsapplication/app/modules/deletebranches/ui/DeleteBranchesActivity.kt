package com.knobblochsapplication.app.modules.deletebranches.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityDeleteBranchesBinding
import com.knobblochsapplication.app.modules.deletebranches.`data`.viewmodel.DeleteBranchesVM
import com.knobblochsapplication.app.modules.menuone.ui.MainActivity
import kotlin.String
import kotlin.Unit

class DeleteBranchesActivity :
    BaseActivity<ActivityDeleteBranchesBinding>(R.layout.activity_delete_branches) {
  private val viewModel: DeleteBranchesVM by viewModels<DeleteBranchesVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.deleteBranchesVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.btn.setOnClickListener {
      //val destIntent = MainActivity.getIntent(this, null)
      //startActivity(destIntent)
    }
    binding.btn1.setOnClickListener {
      //val destIntent = MainActivity.getIntent(this, null)
      //startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "DELETE_BRANCHES_ACTIVITY"

  }
}
