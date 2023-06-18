package com.knobblochsapplication.app.modules.goals.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.elevation.SurfaceColors
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.base.BaseActivity
import com.knobblochsapplication.app.databinding.ActivityGoalsBinding
import com.knobblochsapplication.app.modules.goals.data.viewmodel.GoalsVM
import com.knobblochsapplication.app.modules.goalsunion.ui.GoalsUnionActivity
import java.util.Calendar
import javax.xml.datatype.DatatypeConstants.MONTHS


class GoalsActivity : BaseActivity<ActivityGoalsBinding>(R.layout.activity_goals),
    GoalsAdapter.Listener {
    private val viewModel: GoalsVM by viewModels<GoalsVM>()
    val goalsList = ArrayList<Goal>()
    val adapter = GoalsAdapter(this, goalsList)


    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.goalsVM = viewModel
        binding = ActivityGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun setUpClicks(): Unit {

    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.apply {
            rcView.layoutManager = LinearLayoutManager(this@GoalsActivity)
            rcView.adapter = adapter
            addGoalBtn.setOnClickListener {
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                ft.addToBackStack(null)
                val newFragment: DialogFragment = CreateGoalDialogFragment.newInstance()
                newFragment.show(ft, "dialog")

              /*  bottomSheetView.findViewById<View>(R.id.imageClose3).setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                bottomSheetView.findViewById<View>(R.id.btn2).setOnClickListener {
                    val goal = Goal(
                        if (name.text.toString() != "") name.text.toString() else "Новая цель",
                        deadline.text.toString(),
                        priority.text.toString().toInt(),
                        false,
                        if (description.text.toString() != "") description.text.toString() else "Нет описания"
                    )
                    goalsList.add(goal)
                    adapter.notifyDataSetChanged()
                    bottomSheetDialog.dismiss()
                }
                bottomSheetView.findViewById<View>(R.id.btnArrowRight).setOnClickListener {
                    var number: Int = priority.text.toString().toInt()
                    if (number < 12) number++
                    priority.text = number.toString()
                }
                bottomSheetView.findViewById<View>(R.id.btnArrowLeft).setOnClickListener {
                    var number: Int = priority.text.toString().toInt()
                    if (number > 1) number--
                    priority.text = number.toString()
                }

                bottomSheetView.findViewById<View>(R.id.editDate).setOnClickListener {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)


                    val dpd = DatePickerDialog(this@GoalsActivity, DatePickerDialog.OnDateSetListener { _, pickedYear, monthOfYear, dayOfMonth ->

                        deadline.text = "${if (dayOfMonth > 9) dayOfMonth else ("0$dayOfMonth")}/${if (monthOfYear > 9) monthOfYear else ("0$monthOfYear")}/$pickedYear"

                    }, year, month, day)

                    dpd.show()
                } */


            }

            //frameContainer.setOnClickListener {
            //    finish()
            //}

        }
    }

    companion object {
        const val TAG: String = "GOALS_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, GoalsActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }


    override fun onGoalClick(position: Int) {
        //Toast.makeText(this, "переходим к просмотру подзадач", Toast.LENGTH_LONG).show()
    }

    override fun onLongGoalClick(position: Int) {
        /* val dialog: Dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_menu_union)

        dialog.findViewById<View>(R.id.deleteButton).setOnClickListener {
            val deleteDialog = Dialog(this)
            deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            deleteDialog.setContentView(R.layout.activity_goals_delete)

            deleteDialog.findViewById<View>(R.id.btn).setOnClickListener {
                deleteDialog.dismiss()
            }

            deleteDialog.findViewById<View>(R.id.btn1).setOnClickListener {
                deleteDialog.dismiss()
                dialog.dismiss()
                goalsList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            deleteDialog.show()
        }

        dialog.findViewById<View>(R.id.unionButton).setOnClickListener {

            val intent = Intent(this, GoalsUnionActivity::class.java)
            dialog.dismiss()
            startActivity(intent)
        }

        dialog.findViewById<View>(R.id.changeButton).setOnClickListener {
            val bottomSheetDialog: BottomSheetDialog =
                BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            val bottomSheetView: View = LayoutInflater.from(applicationContext)
                .inflate(R.layout.activity_goals_change, findViewById(R.id.linearChangeGoal))

            val name: EditText = bottomSheetView.findViewById(R.id.editName)
            name.setText(goalsList[position].goalName)

            val deadline: TextView = bottomSheetView.findViewById(R.id.editDate)
            deadline.text = goalsList[position].goalDeadline

            val priority: TextView = bottomSheetView.findViewById(R.id.editPriority)
            priority.text = goalsList[position].goalPriority.toString()

            val isDone: CheckBox = bottomSheetView.findViewById(R.id.isDoneCheckBox)
            isDone.isChecked = goalsList[position].goalIsDone

            val description: EditText = bottomSheetView.findViewById(R.id.editDescription)
            description.setText(goalsList[position].goalDescription)

            bottomSheetView.findViewById<View>(R.id.imageClose3).setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetView.findViewById<View>(R.id.btn2).setOnClickListener {
                val goal = Goal(
                    if (name.text.toString() != "") name.text.toString() else "Новая цель",
                    deadline.text.toString(),
                    priority.text.toString().toInt(),
                    isDone.isChecked,
                    if (description.text.toString() != "") description.text.toString() else "Нет описания"
                )
                goalsList[position] = goal
                adapter.notifyItemChanged(position)
                bottomSheetDialog.dismiss()
                dialog.dismiss()
            }

            bottomSheetView.findViewById<View>(R.id.btnArrowRight).setOnClickListener {
                var number: Int = priority.text.toString().toInt()
                if (number < 12) number++
                priority.text = number.toString()
            }

            bottomSheetView.findViewById<View>(R.id.btnArrowLeft).setOnClickListener {
                var number: Int = priority.text.toString().toInt()
                if (number > 1) number--
                priority.text = number.toString()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        dialog.show()
        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)
        setContentView(binding.root)
        val divider = MaterialDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL)
        divider.isLastItemDecorated = false
        binding.rcView.addItemDecoration(divider) */
    }

    /*override fun setUpClicks(): Unit {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.addGoalBtn.setOnClickListener {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.addToBackStack(null)
            val newFragment: DialogFragment = CreateGoalDialogFragment.newInstance()
            newFragment.show(ft, "dialog")
        }
        binding.rcView.layoutManager = LinearLayoutManager(this@GoalsActivity)
        binding.rcView.adapter = adapter
    }*/

    override fun onBtnDeleteClick(position: Int) {
        /* MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.msg6))
            .setNegativeButton(R.string.lbl21) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.lbl22) { dialog, _ ->
                this.goalsList.removeAt(position)
                adapter.notifyItemRemoved(position)
                dialog.dismiss()
            }
            .show() */
    }

    override fun onBtnEditClick(position: Int) {
        /* val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.addToBackStack(null)
        val newFragment: DialogFragment = EditGoalDialogFragment.newInstance(position)
        newFragment.show(ft, "dialog") */

    }

    override fun onBtnChangeLevelClick(position: Int) {

    }
}
