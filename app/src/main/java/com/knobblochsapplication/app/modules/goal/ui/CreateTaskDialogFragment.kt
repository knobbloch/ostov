package com.knobblochsapplication.app.modules.goal.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.databinding.FragmentCreateTaskBinding
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class CreateTaskDialogFragment : DialogFragment() {
    lateinit var binding: FragmentCreateTaskBinding
    private val appStorage: AppStorage by inject()
    var uid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setStyle(STYLE_NO_TITLE, R.style.fullscreendialog)
        uid = arguments?.getString("uid")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTaskBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnAdd.setOnClickListener {
            var goalActivity = this.activity as GoalActivity
            if (binding.goalName.text.toString().isEmpty()) {
                binding.editName.error = getString(R.string.error_empty_goal_name)
                return@setOnClickListener
            }
            appStorage.addTask(
                goalActivity.lastSelectedGoalUid,
                uid,
                binding.goalName.text.toString(),
                binding.goalDeadline.text.toString(),
                binding.editPriority.text.toString().toInt(),
                binding.goalDescription.text.toString()
            )
            goalActivity.updateView()
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

    @SuppressLint("RestrictedApi")
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
        fun newInstance(uid: String?): CreateTaskDialogFragment {
            val f = CreateTaskDialogFragment()
            val args = Bundle()
            args.putString("uid", uid)
            f.arguments = args
            return f
        }
    }
}