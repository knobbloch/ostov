package com.knobblochsapplication.app.modules.goalschange.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class GoalsChangeModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHeadlinetext: String? = MyApp.getInstance().resources.getString(R.string.lbl15)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.lbl16)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSix: String? = MyApp.getInstance().resources.getString(R.string.lbl17)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSeven: String? = MyApp.getInstance().resources.getString(R.string.lbl18)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtEight: String? = MyApp.getInstance().resources.getString(R.string.lbl19)

)
