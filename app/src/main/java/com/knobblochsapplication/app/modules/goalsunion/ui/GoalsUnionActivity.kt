package com.knobblochsapplication.app.modules.goalsunion.ui

import android.view.View
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalsUnionBinding
import com.knobblochsapplication.app.modules.goalsunion.`data`.model.GoalsUnionRowModel
import com.knobblochsapplication.app.modules.goalsunion.`data`.viewmodel.GoalsUnionVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class GoalsUnionActivity : BaseActivity<ActivityGoalsUnionBinding>(R.layout.activity_goals_union) {
  private val viewModel: GoalsUnionVM by viewModels<GoalsUnionVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    val goalsUnionAdapter = GoalsUnionAdapter(viewModel.goalsUnionList.value?:mutableListOf())
    binding.recyclerGoalsUnion.adapter = goalsUnionAdapter
    goalsUnionAdapter.setOnItemClickListener(
    object : GoalsUnionAdapter.OnItemClickListener {
      override fun onItemClick(view:View, position:Int, item : GoalsUnionRowModel) {
        onClickRecyclerGoalsUnion(view, position, item)
      }
    }
    )
    viewModel.goalsUnionList.observe(this) {
      goalsUnionAdapter.updateData(it)
    }
    binding.goalsUnionVM = viewModel
  }

  override fun setUpClicks(): Unit {
  }

  fun onClickRecyclerGoalsUnion(
    view: View,
    position: Int,
    item: GoalsUnionRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "GOALS_UNION_ACTIVITY"

  }
}
