//package com.knobblochsapplication.app.modules.helpcards//package com.knobblochsapplication.app.modules.helpcards.ui
//
//import androidx.activity.viewModels
//import com.google.android.material.elevation.SurfaceColors
//import com.knobblochsapplication.app.R
//import com.knobblochsapplication.app.appcomponents.base.BaseActivity
//import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
//import com.knobblochsapplication.app.databinding.ActivityHelpCards2Binding
//import com.knobblochsapplication.app.modules.helpcards.data.viewmodel.HelpCardsVM
//import org.koin.android.ext.android.inject
//
//class HelpCards :
//    BaseActivity<ActivityHelpCards2Binding>(R.layout.activity_help_cards2) {
//    private val viewModel: HelpCardsVM by viewModels()
//    private val preferenceHelper: PreferenceHelper by inject()
//
//    override fun onInitialized(): Unit {
//        viewModel.navArguments = intent.extras?.getBundle("bundle")
//        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
//        binding.diagramViewVM = viewModel
//    }
//
//    override fun setUpClicks(): Unit {
//        binding.topAppBar.setNavigationOnClickListener {
//            finish()
//        }
//
//    }
//
//    companion object {
//        const val TAG: String = "HELP_CARDS_ACTIVITY"
//
//    }
//}
//
