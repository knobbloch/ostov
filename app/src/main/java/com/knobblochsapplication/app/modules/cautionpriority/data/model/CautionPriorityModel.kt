package com.knobblochsapplication.app.modules.cautionpriority.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class CautionPriorityModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyTwo: String? = MyApp.getInstance().resources.getString(R.string.msg22)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_13)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwo: String? = MyApp.getInstance().resources.getString(R.string.lbl_24)

)