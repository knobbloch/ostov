package com.knobblochsapplication.app.modules.main.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp

data class GoalsModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtText: String? = MyApp.getInstance().resources.getString(R.string.lbl)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtNamegoal: String? = MyApp.getInstance().resources.getString(R.string.lbl_1)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtAboutgoal: String? = MyApp.getInstance().resources.getString(R.string.lbl2)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var deadline: String? = MyApp.getInstance().resources.getString(R.string.lbl17)
)
