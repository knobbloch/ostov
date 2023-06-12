
<<<<<<< Updated upstream
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalSchemeBinding
import com.knobblochsapplication.app.modules.goalscheme.`data`.viewmodel.GoalSchemeVM
import com.knobblochsapplication.app.modules.menuone.ui.MenuOneActivity
import com.knobblochsapplication.app.modules.percentageofgoal.ui.PercentageOfGoalActivity
import kotlin.String
import kotlin.Unit

class GoalSchemeActivity : BaseActivity<ActivityGoalSchemeBinding>(R.layout.activity_goal_scheme) {
  private val viewModel: GoalSchemeVM by viewModels<GoalSchemeVM>()

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.goalSchemeVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.txtSeventyFive.setOnClickListener {
      val destIntent = PercentageOfGoalActivity.getIntent(this, null)
      startActivity(destIntent)
    }
    binding.imageMenu.setOnClickListener {
      val destIntent = MenuOneActivity.getIntent(this, null)
      startActivity(destIntent)
    }
  }

  companion object {
    const val TAG: String = "GOAL_SCHEME_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, GoalSchemeActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
=======
>>>>>>> Stashed changes
