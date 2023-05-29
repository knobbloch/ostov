package com.knobblochsapplication.app.modules.addtask.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class AddTaskModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHeadlinetext: String? = MyApp.getInstance().resources.getString(R.string.msg17)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.lbl16)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwentyFive: String? = MyApp.getInstance().resources.getString(R.string.lbl17)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwentySix: String? = MyApp.getInstance().resources.getString(R.string.lbl29)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwentySeven: String? = MyApp.getInstance().resources.getString(R.string.lbl19)

)
