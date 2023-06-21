package com.knobblochsapplication.app.modules.downloadlist.ui

<<<<<<< Updated upstream
import android.view.View
=======
>>>>>>> Stashed changes
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.appcomponents.utility.Doxc_file
import com.knobblochsapplication.app.databinding.ActivityDownloadListBinding
import com.knobblochsapplication.app.modules.Docx.Doxc_File
import com.knobblochsapplication.app.modules.File_system.File_Manager
import com.knobblochsapplication.app.modules.downloadlist.`data`.model.DownloadListRowModel
import com.knobblochsapplication.app.modules.downloadlist.`data`.viewmodel.DownloadListVM
import kotlin.Int
import kotlin.String
import kotlin.Unit

class DownloadListActivity :
    BaseActivity<ActivityDownloadListBinding>(R.layout.activity_download_list) {
  private val viewModel: DownloadListVM by viewModels<DownloadListVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
    val downloadListAdapter =
    DownloadListAdapter(viewModel.downloadListList.value?:mutableListOf())
//    binding.recyclerDownloadList.adapter = downloadListAdapter
//    downloadListAdapter.setOnItemClickListener(
//    object : DownloadListAdapter.OnItemClickListener {
//      override fun onItemClick(view:View, position:Int, item : DownloadListRowModel) {
//        onClickRecyclerDownloadList(view, position, item)
//      }
//    }
//    )
    viewModel.downloadListList.observe(this) {
      downloadListAdapter.updateData(it)
    }
    binding.downloadListVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.topAppBar.setNavigationOnClickListener {
      finish()
    }
<<<<<<< Updated upstream
    binding.buttonDownload.setOnClickListener {
        //Здесь написан код для того,чтобы скачивать докс(вместе с сортировками), но так как я не могу
        //его пока проверить,то он будет закомментирован
        //Миша из будущего,не забудь,что нужно поменять все 1 на передаваемое значение с прошлого экрана!

//      var goal_id: Int = intent.extras!!.getInt("goalId",-1)
//      System.out.println(goal_id)

//      val important_check_box = binding.importantCheckBox.isChecked
//      val readiness_check_box = binding.readinessCheckBox.isChecked
//      val date_check_box = binding.dateCheckBox.isChecked
//
//      val radio_button_important = binding.radioButton3.isChecked
//      val radio_button_readiness = binding.radioButton4.isChecked
//
//
//
//      if (important_check_box == false && readiness_check_box == false && date_check_box == false) {
//        Toast.makeText(this@DownloadListActivity, R.string.you_did_not_chose_parametrs, Toast.LENGTH_LONG)
//          .show()
//      }
//          if (important_check_box && readiness_check_box == false && date_check_box == false) {
//              File_Manager.sort_by_rang(1)
//          }
//          if (important_check_box == false && readiness_check_box && date_check_box == false) {
//              File_Manager.sort_by_complete(1)
//          }
//          if (important_check_box == false && readiness_check_box == false && date_check_box) {
//              File_Manager.sort_by_expires(1)
//          }
//          if (important_check_box && readiness_check_box == false && date_check_box) {
//              File_Manager.sort_by_rang(1)
//              File_Manager.sort_by_expires(1)
//          }
//          if (important_check_box == false && readiness_check_box && date_check_box) {
//              File_Manager.sort_by_expires(1)
//              File_Manager.sort_by_complete(1)
//          }
//          if (important_check_box && readiness_check_box && date_check_box == false) {
//              if (radio_button_important) {
//                  File_Manager.sort_by_complete(1)
//                  File_Manager.sort_by_rang(1)
//              } else {
//                  File_Manager.sort_by_rang(1)
//                  File_Manager.sort_by_complete(1)
//              }
//          }
//          if (important_check_box && readiness_check_box && date_check_box) {
//              if (radio_button_important) {
//                  File_Manager.sort_by_complete(1)
//                  File_Manager.sort_by_rang(1)
//                  File_Manager.sort_by_expires(1)
//              } else {
//                  File_Manager.sort_by_rang(1)
//                  File_Manager.sort_by_expires(1)
//                  File_Manager.sort_by_complete(1)
//              }
//          }
//      //Надо получть переменную
//      Doxc_File.make_docx(this,1)
    }
=======

    binding.buttonDownload.setOnClickListener {
      val important_check_box = binding.importantCheckBox.isChecked
      val readiness_check_box = binding.readinessCheckBox.isChecked
      val date_check_box = binding.dateCheckBox.isChecked

      val radio_button_important = binding.radioButton3.isChecked
      val radio_button_readiness = binding.radioButton4.isChecked

      var do_we_do=true

      if (important_check_box == false && readiness_check_box == false && date_check_box == false) {
        Toast.makeText(this@DownloadListActivity, R.string.you_did_not_chose_parametrs, Toast.LENGTH_LONG)
          .show()
        do_we_do=false
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
            Toast.makeText(this@DownloadListActivity, R.string.you_did_not_chose_pref, Toast.LENGTH_LONG)
              .show()
          do_we_do=false
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
          Toast.makeText(this@DownloadListActivity, R.string.you_did_not_chose_pref, Toast.LENGTH_LONG)
            .show()
          do_we_do=false
        }
        }
      if (do_we_do){
        Doxc_file.make_docx(this,1)
      }
      }
>>>>>>> Stashed changes
  }

  fun onClickRecyclerDownloadList(
    view: View,
    position: Int,
    item: DownloadListRowModel
  ): Unit {
    when(view.id) {
    }
  }

  companion object {
    const val TAG: String = "DOWNLOAD_LIST_ACTIVITY"

  }
}
