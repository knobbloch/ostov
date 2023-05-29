package com.knobblochsapplication.app.modules.downloadlist.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class DownloadListModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtText: String? = MyApp.getInstance().resources.getString(R.string.msg2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTextOne: String? = MyApp.getInstance().resources.getString(R.string.msg3)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTextFive: String? = MyApp.getInstance().resources.getString(R.string.msg4)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTextSix: String? = MyApp.getInstance().resources.getString(R.string.lbl11)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTextSeven: String? = MyApp.getInstance().resources.getString(R.string.lbl12)

)
