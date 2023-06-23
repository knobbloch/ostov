package com.knobblochsapplication.app.modules.sort.ui

import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.appcomponents.utility.SortType
import com.knobblochsapplication.app.databinding.ActivitySortBinding
import com.knobblochsapplication.app.modules.sort.data.viewmodel.SortVM
import org.koin.android.ext.android.inject

class SortActivity : BaseActivity<ActivitySortBinding>(R.layout.activity_sort) {
    private val viewModel: SortVM by viewModels<SortVM>()
    private val preferenceHelper: PreferenceHelper by inject()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        binding.sortVM = viewModel
    }

    override fun setUpClicks(): Unit {
        when (preferenceHelper.getSortType()) {
            SortType.BY_PRIORITY -> {
                binding.date.isChecked = false
                binding.priority.isChecked = true
                binding.completion.isChecked = false
                binding.priorityDate.isChecked = false
            }
            SortType.BY_DEADLINE -> {
                binding.date.isChecked = true
                binding.priority.isChecked = false
                binding.completion.isChecked = false
                binding.priorityDate.isChecked = false
            }
            SortType.BY_COMPLETION -> {
                binding.date.isChecked = false
                binding.priority.isChecked = false
                binding.completion.isChecked = true
                binding.priorityDate.isChecked = false
            }
            SortType.BY_PRIORITY_DEADLINE -> {
                binding.date.isChecked = false
                binding.priority.isChecked = false
                binding.completion.isChecked = false
                binding.priorityDate.isChecked = true
            }
        }
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.btnSort.setOnClickListener {
            var selected = SortType.BY_PRIORITY
            when (binding.sort.checkedRadioButtonId) {
                R.id.priority -> {
                    selected = SortType.BY_PRIORITY
                }
                R.id.completion -> {
                    selected = SortType.BY_COMPLETION
                }
                R.id.date -> {
                    selected = SortType.BY_DEADLINE
                }
                R.id.priority_date -> {
                    selected = SortType.BY_PRIORITY_DEADLINE
                }
            }
            preferenceHelper.setSortType(selected)
            this.finish()
        }
    }

    companion object {
        const val TAG: String = "SORT_ACTIVITY"
    }
}
