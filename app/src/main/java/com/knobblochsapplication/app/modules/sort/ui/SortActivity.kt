package com.knobblochsapplication.app.modules.sort.ui

import android.view.View
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivitySortBinding
import com.knobblochsapplication.app.modules.sort.`data`.model.SortRowModel
import com.knobblochsapplication.app.modules.sort.`data`.viewmodel.SortVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class SortActivity : BaseActivity<ActivitySortBinding>(R.layout.activity_sort) {
  private val viewModel: SortVM by viewModels<SortVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val sortAdapter = SortAdapter(viewModel.sortList.value?:mutableListOf())
    binding.recyclerSort.adapter = sortAdapter
    sortAdapter.setOnItemClickListener(
    object : SortAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : SortRowModel) {
        onClickRecyclerSort(view, position, item)
      }
    }
    )
    viewModel.sortList.observe(this) {
      sortAdapter.updateData(it)
    }
    binding.sortVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }
  }

  fun onClickRecyclerSort(
    view: View,
    position: Int,
    item: SortRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "SORT_ACTIVITY"

  }
}
