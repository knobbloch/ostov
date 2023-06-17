package com.knobblochsapplication.app.modules.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.appcomponents.utility.PreferenceHelper
import com.knobblochsapplication.app.databinding.FragmentEditTaskBinding
import com.knobblochsapplication.app.modules.goal.ui.GoalActivity
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class EditTaskDialogFragment : DialogFragment() {
    lateinit var binding: FragmentEditTaskBinding
    private val appStorage: AppStorage by inject()
    private val preferenceHelper: PreferenceHelper by inject()
    var position = 0
    var uid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for fullscreen
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setStyle(STYLE_NO_TITLE, R.style.fullscreendialog)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val goalUid = preferenceHelper.getLastSelectedGoal()
        binding = FragmentEditTaskBinding.inflate(layoutInflater)
        if (goalUid != null && uid != null) {
            val goal = appStorage.getGoalByUid(goalUid)
            if (goal != null) {
                val task = goal.getTaskByUid(uid!!)
                if (task != null) {
                    binding.goalDeadline.setText(task.deadline)
                    binding.editPriority.text = task.priority.toString()
                    binding.finished.isChecked = task.isDone
                    binding.goalDescription.setText(task.description)

                }

            }

        }
        binding.goalName.setText(uid)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            var goalActivity = this.activity as GoalActivity
            if (binding.goalName.text.toString().isEmpty()){
                binding.editName.error = getString(R.string.error_empty_goal_name)
                return@setOnClickListener
            }
            var task = appStorage.getGoalByUid(goalActivity.lastSelectedGoalUid)
            if (task == null) {
                return@setOnClickListener
            }
            task.name = binding.goalName.text.toString()
            task.deadline = binding.goalDeadline.text.toString()
            task.priority = binding.editPriority.text.toString().toInt()
            task.isDone = binding.finished.isChecked
            task.description = binding.goalDescription.text.toString()
            dismiss()
        }
        binding.editDate.setEndIconOnClickListener {
            binding.editDate.editText?.setText("")
        }
        binding.editDate.setStartIconOnClickListener {
            showDatePicker()
        }
        binding.goalDeadline.setOnClickListener() {
            showDatePicker()
        }
        binding.btnArrowRight.setOnClickListener {
            var number: Int = binding.editPriority.text.toString().toInt()
            if (number < 12) number++
            binding.editPriority.text = number.toString()
        }
        binding.btnArrowLeft.setOnClickListener {
            var number: Int = binding.editPriority.text.toString().toInt()
            if (number > 1) number--
            binding.editPriority.text = number.toString()
        }
    }

    fun showDatePicker() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setFirstDayOfWeek(Calendar.MONDAY)
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.lbl17))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        datePicker.show(this.parentFragmentManager, null)
        datePicker.addOnPositiveButtonClickListener {
            val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            binding.editDate.editText?.setText(outputDateFormat.format(it))
        }
    }

    companion object {
        fun newInstance(position:Int, uid: String): EditTaskDialogFragment {
            val f = EditTaskDialogFragment()
            val args = Bundle()
            args.putInt("position", position)
            args.putString("uid", uid)
            f.arguments = args
            return f
        }
    }
}