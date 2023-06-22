package com.knobblochsapplication.app.modules.downloadlist.ui

import android.os.Environment
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityDownloadListBinding
import com.knobblochsapplication.app.modules.downloadlist.data.viewmodel.DownloadListVM
import org.koin.android.ext.android.inject

class DownloadListActivity :
    BaseActivity<ActivityDownloadListBinding>(R.layout.activity_download_list) {
  private val viewModel: DownloadListVM by viewModels()
  private val appStorage: AppStorage by inject()
  private val preferenceHelper: PreferenceHelper by inject()

  override fun onInitialized() {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
//    val downloadListAdapter =
//    DownloadListAdapter(viewModel.downloadListList.value?:mutableListOf())
//    binding.recyclerDownloadList.adapter = downloadListAdapter
//    downloadListAdapter.setOnItemClickListener(
//    object : DownloadListAdapter.OnItemClickListener {
//      override fun onItemClick(view:View, position:Int, item : DownloadListRowModel) {
//        onClickRecyclerDownloadList(view, position, item)
//      }
//    }
//    )
    viewModel.downloadListList.observe(this) {
//      downloadListAdapter.updateData(it)
    }
    binding.downloadListVM = viewModel
  }

  override fun setUpClicks() {
    binding.topAppBar.setNavigationOnClickListener {
      finish()
    }
    val uid = preferenceHelper.getLastSelectedGoal()
    binding.buttonDownload.setOnClickListener {
        appStorage.downloadDocxFile(uid!!)
        Toast.makeText(
            this,
            "Документ был успешно создан в папке " + Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ).name,
            Toast.LENGTH_LONG
        ).show()
        this.finish()
    }

//      binding.buttonDownload.setOnClickListener {
//          val important_check_box = binding.importantCheckBox.isChecked
//          val readiness_check_box = binding.readinessCheckBox.isChecked
//          val date_check_box = binding.dateCheckBox.isChecked
//
//          val radio_button_important = binding.radioButton3.isChecked
//          val radio_button_readiness = binding.radioButton4.isChecked
//
//          var do_we_do=true
//
//          if (important_check_box == false && readiness_check_box == false && date_check_box == false) {
//              Toast.makeText(this@DownloadListActivity, R.string.you_did_not_chose_parametrs, Toast.LENGTH_LONG)
//                  .show()
//              do_we_do=false
//          }
//          if (important_check_box && readiness_check_box == false && date_check_box == false) {
//              //Сортировать по рангу
//          }
//          if (important_check_box == false && readiness_check_box && date_check_box == false) {
//              //Сортировать по выполненности
//          }
//          if (important_check_box == false && readiness_check_box == false && date_check_box) {
//              //Сортировать по дедлайну
//          }
//          if (important_check_box && readiness_check_box == false && date_check_box) {
//              //Сортировать по рангу
//              //Сортировать по дедлайну
//          }
//          if (important_check_box == false && readiness_check_box && date_check_box) {
//              //Сортировать по дедлайну
//              //Сортировать по выполненности
//          }
//          if (important_check_box && readiness_check_box && date_check_box == false) {
//              if (radio_button_important) {
//                  //Сортировать по выполненности
//                  //Сортировать по рангу
//              }
//              if (radio_button_readiness) {
//                  //Сортировать по рангу
//                  //Сортировать по выполненности
//              }
//              if(!radio_button_readiness && !radio_button_important) {
//                  Toast.makeText(this@DownloadListActivity, R.string.you_did_not_chose_pref, Toast.LENGTH_LONG)
//                      .show()
//                  do_we_do=false
//              }
//          }
//          if (important_check_box && readiness_check_box && date_check_box) {
//              if (radio_button_important) {
//                  //Сортировать по выполненности
//                  //Сортировать по рангу
//              }
//              if (radio_button_readiness) {
//                  //Сортировать по рангу
//                  //Сортировать по выполненности
//              }
//              if(!radio_button_readiness && !radio_button_important) {
//                  Toast.makeText(this@DownloadListActivity, R.string.you_did_not_chose_pref, Toast.LENGTH_LONG)
//                      .show()
//                  do_we_do=false
//              }
//          }
//          if (do_we_do){
//              Doxc_file.make_docx(this,1)
//          }
//      }
}


  companion object {
    const val TAG: String = "DOWNLOAD_LIST_ACTIVITY"

  }
}
