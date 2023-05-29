package com.knobblochsapplication.app.modules.helpscreenfour.`data`.model

import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import kotlin.String

data class HelpScreenFourModel(
  /**
   * TODO Replace with dynamic value
   */
  var txtTextmessage: String? = MyApp.getInstance().resources.getString(R.string.msg_52)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtText: String? = MyApp.getInstance().resources.getString(R.string.msg9)
  ,
  /**
   * TODO Replace with dynamic value
   */
  var txtAboutsettings: String? = MyApp.getInstance().resources.getString(R.string.msg10)

)
