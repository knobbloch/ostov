package com.knobblochsapplication.app.modules.diagramview.ui

import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityDiagramViewBinding
import com.knobblochsapplication.app.modules.diagramview.`data`.viewmodel.DiagramViewVM
import kotlin.String
import kotlin.Unit

class DiagramViewActivity : BaseActivity<ActivityDiagramViewBinding>(R.layout.activity_diagram_view)
    {
  private val viewModel: DiagramViewVM by viewModels<DiagramViewVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.diagramViewVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  companion object {
    const val TAG: String = "DIAGRAM_VIEW_ACTIVITY"

  }
}
