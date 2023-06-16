package com.knobblochsapplication.app.modules.diagramview.ui

import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityDiagramViewBinding
import com.knobblochsapplication.app.modules.diagramview.data.viewmodel.DiagramViewVM
import org.koin.android.ext.android.inject

class DiagramViewActivity :
    BaseActivity<ActivityDiagramViewBinding>(R.layout.activity_diagram_view) {
    private val viewModel: DiagramViewVM by viewModels()
    private val preferenceHelper: PreferenceHelper by inject()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        binding.diagramViewVM = viewModel
        if (preferenceHelper.isDiagramSelected()) {
            binding.radioGroup.check(R.id.diagramView)
        } else {
            binding.radioGroup.check(R.id.listView)
        }
    }

    override fun setUpClicks(): Unit {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.radioGroup.setOnCheckedChangeListener { bb, i ->
            if (R.id.diagramView == bb.checkedRadioButtonId) {
                preferenceHelper.setDiagramSelected(true)
            } else {
                preferenceHelper.setDiagramSelected(false)
            }
        }
    }

    companion object {
        const val TAG: String = "DIAGRAM_VIEW_ACTIVITY"

    }
}
