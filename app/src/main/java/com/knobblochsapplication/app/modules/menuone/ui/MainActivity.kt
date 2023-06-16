package com.knobblochsapplication.app.modules.menuone.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.view.Window
import android.view.animation.ScaleAnimation
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.ActivityGoalSchemeBinding
import com.knobblochsapplication.app.databinding.ActivityMainMenuBinding
import com.knobblochsapplication.app.modules.File_system.File_Manager
import com.knobblochsapplication.app.modules.File_system.Goal
import com.knobblochsapplication.app.modules.diagramview.ui.DiagramViewActivity
import com.knobblochsapplication.app.modules.downloadlist.ui.DownloadListActivity
import com.knobblochsapplication.app.modules.goalsunion.ui.GoalsUnionActivity
import com.knobblochsapplication.app.modules.helpscreenone.ui.HelpScreenOneActivity
import com.knobblochsapplication.app.modules.settings.ui.SettingsActivity
import com.knobblochsapplication.app.modules.sort.ui.SortActivity
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(), MenuAdapter.Listener {
    lateinit var binding: ActivityMainMenuBinding

    private val preferenceHelper: PreferenceHelper by inject()

    private var mScale = 1f
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    var gestureDetector: GestureDetector? = null
    lateinit var bindLayout: ActivityGoalSchemeBinding
    val goalId: Int = 0

    private val goalsList = ArrayList<Goal>()
    ///private val goalsList = ParentItemList()

    //private val goalsList = ArrayList<Goal>()
    private val adapter = MenuAdapter(this, goalsList)

    override fun onCreate(savedInstanceState: Bundle?) {

        File_Manager.Write_goal( "Первая зddадача", "Это первая задача", 1000, 1);
        File_Manager.Write_task(1,  "Вторая задача", "Это вторая задача", 20, 10);
        File_Manager.Write_task(1,  "Третья задача", "Это третья задача", 200, 7);
        File_Manager.Write_task(1,  "Четвёртая задача", "Это четвёртая задача", 40, 3);

        File_Manager.connect_children_to_parent(1, 2, 1);
        File_Manager.connect_children_to_parent(1, 3, 1);
        File_Manager.connect_children_to_parent(1, 4, 2);

        for (i in File_Manager.Find_task_by_id(File_Manager.listFiles().get(0), File_Manager.listFiles().get(0)).children){
            goalsList.add(File_Manager.Find_task_by_id(File_Manager.listFiles().get(0), i))
        }

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
                val layout = binding.l2.layout

                // we are setting it as animation
                layout.startAnimation(scaleAnimation)
                return true
            }
        })
        bindLayout = binding.l2
        InitFish()
    }

    private fun InitFish() {

        val ParentRecyclerViewItem: RecyclerView = bindLayout.rcView
        val layoutManager = LinearLayoutManager(this@MainActivity)
        val parentItemAdapter = MenuAdapter(this, goalsList)
        ParentRecyclerViewItem.setAdapter(parentItemAdapter)
        ParentRecyclerViewItem.setLayoutManager(layoutManager)



        bindLayout.apply {
            rcView.layoutManager = LinearLayoutManager(bindLayout.l2.context)
            rcView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onBranchClick(position: Int, goal: Goal) {
        val a = 3
        val dialog: Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_menu_two)

        dialog.show()
    }

    override fun onLongBranchClick(position: Int, goal: Goal) {
        val a = 3
        //goalsList.removeAt(position)
        //adapter.notifyItemRemoved(position)
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