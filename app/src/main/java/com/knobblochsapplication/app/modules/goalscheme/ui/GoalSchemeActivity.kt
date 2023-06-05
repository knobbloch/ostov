package com.knobblochsapplication.app.modules.goalscheme.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.animation.ScaleAnimation
import androidx.activity.viewModels
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalSchemeBinding
import com.knobblochsapplication.app.modules.goals.ui.GoalsActivity
import com.knobblochsapplication.app.modules.goalscheme.data.viewmodel.GoalSchemeVM
import com.knobblochsapplication.app.modules.menuone.ui.MenuOneActivity
import com.knobblochsapplication.app.modules.percentageofgoal.ui.PercentageOfGoalActivity


class GoalSchemeActivity : BaseActivity<ActivityGoalSchemeBinding>(R.layout.activity_goal_scheme) {
  private val viewModel: GoalSchemeVM by viewModels<GoalSchemeVM>()
  private val TAG = "MainActivity"
  private var mScale = 1f
  private var mScaleGestureDetector: ScaleGestureDetector? = null
  var gestureDetector: GestureDetector? = null

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.goalSchemeVM = viewModel
    gestureDetector = GestureDetector(this, GestureDetector.SimpleOnGestureListener())
    mScaleGestureDetector = ScaleGestureDetector(this, object : SimpleOnScaleGestureListener() {
      override fun onScale(detector: ScaleGestureDetector): Boolean {

        // firstly we will get the scale factor
        val scale = 1 - detector.scaleFactor
        val prevScale: Float = mScale
        mScale += scale

        // we can maximise our focus to 10f only
        if (mScale > 10f) mScale = 10f
        val scaleAnimation = ScaleAnimation(
          1f / prevScale,
          1f / mScale,
          1f / prevScale,
          1f / mScale,
          detector.focusX,
          detector.focusY
        )

        // duration of animation will be 0.It will
        // not change by self after that
        scaleAnimation.duration = 0
        scaleAnimation.fillAfter = true

        // initialising the scrollview
        val layout = binding.scrollView

        // we are setting it as animation
        layout.startAnimation(scaleAnimation)
        return true
      }
    })
  }

  override fun setUpClicks(): Unit {
    binding.txtSeventyFive.setOnClickListener {
      val destIntent = Intent(this, PercentageOfGoalActivity::class.java)
      startActivity(destIntent)
    }
    binding.imageMenu.setOnClickListener {
      val destIntent = Intent(this, MenuOneActivity::class.java)
      startActivity(destIntent)
    }

    binding.txtMygoals.setOnClickListener {
      val targetIntent = Intent(this, GoalsActivity::class.java)
      startActivity(targetIntent)
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

  override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
    super.dispatchTouchEvent(event)

    // special types of touch screen events such as pinch ,
    // double tap, scrolls , long presses and flinch,
    // onTouch event is called if found any of these
    mScaleGestureDetector!!.onTouchEvent(event!!)
    gestureDetector!!.onTouchEvent(event)
    return gestureDetector!!.onTouchEvent(event)
  }

  private class GestureListener : SimpleOnGestureListener() {
    override fun onDown(e: MotionEvent): Boolean {
      return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
      return true
    }
  }
}
