package com.knobblochsapplication.app.modules.sort.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class SortModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtText: String? = MyApp.getInstance().resources.getString(R.string.lbl14)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTextFour: String? = MyApp.getInstance().resources.getString(R.string.msg4)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTextFive: String? = MyApp.getInstance().resources.getString(R.string.lbl11)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTextSix: String? = MyApp.getInstance().resources.getString(R.string.lbl12)

)
