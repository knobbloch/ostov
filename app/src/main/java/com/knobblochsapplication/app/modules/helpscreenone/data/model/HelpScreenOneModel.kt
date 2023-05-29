package com.knobblochsapplication.app.modules.helpscreenone.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class HelpScreenOneModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtFourteen: String? = MyApp.getInstance().resources.getString(R.string.lbl)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtDescription: String? = MyApp.getInstance().resources.getString(R.string.msg11)

)
