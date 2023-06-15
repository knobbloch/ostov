package com.knobblochsapplication.app.modules.menuone.ui

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityMainMenuBinding
import com.knobblochsapplication.app.modules.diagramview.ui.DiagramViewActivity
import com.knobblochsapplication.app.modules.downloadlist.ui.DownloadListActivity
import com.knobblochsapplication.app.modules.helpscreenone.ui.HelpScreenOneActivity
import com.knobblochsapplication.app.modules.settings.ui.SettingsActivity
import com.knobblochsapplication.app.modules.sort.ui.SortActivity
import org.koin.android.ext.android.inject
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.animation.ScaleAnimation

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainMenuBinding
    private val preferenceHelper: PreferenceHelper by inject()
    private var mScale = 1f
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    var gestureDetector: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
//        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)

        if(savedInstanceState == null){
            if (!preferenceHelper.isHelpPageShowed()) {
                println("isHelpPageShowed false")
                val myIntent = Intent(this, HelpScreenOneActivity::class.java)
                this.startActivity(myIntent)
            } else {
                println("isHelpPageShowed true")
            }

            if (preferenceHelper.isDarkTheme()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.apply {
            topAppBar.setNavigationOnClickListener {
                drawer.openDrawer(GravityCompat.START)
            }
        }

        gestureDetector = GestureDetector(this, GestureListener())
        mScaleGestureDetector = ScaleGestureDetector(this, object : SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {

                // firstly we will get the scale factor
                val scale = 1 - detector.scaleFactor
                val prevScale = mScale
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
                val layout = binding.l2.scrollView11

                // we are setting it as animation
                layout.startAnimation(scaleAnimation)
                return true
            }
        })
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

    private class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            return true
        }
    }

    fun onClickGoSettings(item: MenuItem) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoDiagramView(item: MenuItem) {
        val intent = Intent(this, DiagramViewActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoSortType(item: MenuItem) {
        val intent = Intent(this, SortActivity::class.java)
        startActivity(intent)
    }

    fun onClickGoDownloadList(item: MenuItem) {
        val intent = Intent(this, DownloadListActivity::class.java)
        startActivity(intent)
    }

}