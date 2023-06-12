package com.knobblochsapplication.app.modules.sort.addinganewgoal.data.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class AddingANewGoalModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHeadlinetext: String? = MyApp.getInstance().resources.getString(R.string.msg14)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtLanguage: String? = MyApp.getInstance().resources.getString(R.string.lbl16)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtText: String? = MyApp.getInstance().resources.getString(R.string.lbl17)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtRang: String? = MyApp.getInstance().resources.getString(R.string.lbl29)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtTextOne: String? = MyApp.getInstance().resources.getString(R.string.lbl19)

)
