package com.knobblochsapplication.app.modules.sort.ui

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivitySortBinding
import com.knobblochsapplication.app.modules.sort.data.model.SortRowModel
import com.knobblochsapplication.app.modules.sort.data.viewmodel.SortVM

class SortActivity : BaseActivity<ActivitySortBinding>(R.layout.activity_sort) {
  private val viewModel: SortVM by viewModels<SortVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
    val sortAdapter = SortAdapter(viewModel.sortList.value?:mutableListOf())
//    binding.recyclerSort.adapter = sortAdapter
//    sortAdapter.setOnItemClickListener(
//    object : SortAdapter.OnItemClickListener {
//      override fun onItemClick(view:View, position:Int, item : SortRowModel) {
//        onClickRecyclerSort(view, position, item)
//      }
//    }
//    )
    viewModel.sortList.observe(this) {
      sortAdapter.updateData(it)
    }
    binding.sortVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.topAppBar.setNavigationOnClickListener {
      finish()
    }

      binding.buttonDownload.setOnClickListener {
          val important_check_box = binding.importantCheckBox.isChecked
          val readiness_check_box = binding.readinessCheckBox.isChecked
          val date_check_box = binding.dateCheckBox.isChecked

          val radio_button_important = binding.radioButton3.isChecked
          val radio_button_readiness = binding.radioButton4.isChecked

          if (important_check_box == false && readiness_check_box == false && date_check_box == false) {
              Toast.makeText(this@SortActivity, R.string.you_did_not_chose_parametrs, Toast.LENGTH_LONG)
                  .show()
          }
          if (important_check_box && readiness_check_box == false && date_check_box == false) {
              //Сортировать по рангу
          }
          if (important_check_box == false && readiness_check_box && date_check_box == false) {
              //Сортировать по выполненности
          }
          if (important_check_box == false && readiness_check_box == false && date_check_box) {
              //Сортировать по дедлайну
          }
          if (important_check_box && readiness_check_box == false && date_check_box) {
              //Сортировать по рангу
              //Сортировать по дедлайну
          }
          if (important_check_box == false && readiness_check_box && date_check_box) {
              //Сортировать по дедлайну
              //Сортировать по выполненности
          }
          if (important_check_box && readiness_check_box && date_check_box == false) {
              if (radio_button_important) {
                  //Сортировать по выполненности
                  //Сортировать по рангу
              }
              if (radio_button_readiness) {
                  //Сортировать по рангу
                  //Сортировать по выполненности
              }
              if(!radio_button_readiness && !radio_button_important) {
                  Toast.makeText(this@SortActivity, R.string.you_did_not_chose_pref, Toast.LENGTH_LONG)
                      .show()
              }
          }
          if (important_check_box && readiness_check_box && date_check_box) {
              if (radio_button_important) {
                  //Сортировать по выполненности
                  //Сортировать по рангу
              }
              if (radio_button_readiness) {
                  //Сортировать по рангу
                  //Сортировать по выполненности
              }
              if(!radio_button_readiness && !radio_button_important) {
                  Toast.makeText(this@SortActivity, R.string.you_did_not_chose_pref, Toast.LENGTH_LONG)
                      .show()
              }
          }
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
