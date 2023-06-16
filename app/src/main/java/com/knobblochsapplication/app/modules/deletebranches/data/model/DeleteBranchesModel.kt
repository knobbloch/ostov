package com.knobblochsapplication.app.modules.deletebranches.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class DeleteBranchesModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyThree: String? = MyApp.getInstance().resources.getString(R.string.lbl34)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyFour: String? = MyApp.getInstance().resources.getString(R.string.msg23)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtThirtyFive: String? = MyApp.getInstance().resources.getString(R.string.lbl35)

)
