package com.knobblochsapplication.app.modules.changetask.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class ChangeTaskModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHeadlinetext: String? = MyApp.getInstance().resources.getString(R.string.msg19)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.lbl16)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwentyEight: String? = MyApp.getInstance().resources.getString(R.string.lbl17)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTwentyNine: String? = MyApp.getInstance().resources.getString(R.string.lbl29)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirty: String? = MyApp.getInstance().resources.getString(R.string.lbl18)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyOne: String? = MyApp.getInstance().resources.getString(R.string.lbl19)

)
