package com.knobblochsapplication.app.modules.goalscheme.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class GoalSchemeModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtMygoals: String? = MyApp.getInstance().resources.getString(R.string.lbl)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSeventyFive: String? = MyApp.getInstance().resources.getString(R.string.lbl_75)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtYourgoal: String? = MyApp.getInstance().resources.getString(R.string.lbl25)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTaskOne: String? = MyApp.getInstance().resources.getString(R.string.lbl_12)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTaskTwo: String? = MyApp.getInstance().resources.getString(R.string.lbl_23)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTaskThree: String? = MyApp.getInstance().resources.getString(R.string.lbl_32)

)
