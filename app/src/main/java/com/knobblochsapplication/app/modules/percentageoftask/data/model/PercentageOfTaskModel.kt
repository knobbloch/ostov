package com.knobblochsapplication.app.modules.percentageoftask.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class PercentageOfTaskModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtHeadlinetext: String? = MyApp.getInstance().resources.getString(R.string.msg15)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtSeventyFive: String? = MyApp.getInstance().resources.getString(R.string.lbl_752)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtHeadlinetextOne: String? = MyApp.getInstance().resources.getString(R.string.msg16)

)
