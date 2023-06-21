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
import com.knobblochsapplication.app.databinding.FragmentEditGoalBinding
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class EditGoalDialogFragment : DialogFragment() {
    lateinit var binding: FragmentEditGoalBinding
    private val appStorage: AppStorage by inject()
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for fullscreen
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setStyle(STYLE_NO_TITLE, R.style.fullscreendialog)
        position = arguments?.getInt("position")!!
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditGoalBinding.inflate(layoutInflater)
        val goal = appStorage.goals[position]
        binding.goalName.setText(goal.name)
        binding.goalDeadline.setText(goal.deadline)
        binding.editPriority.text = goal.priority.toString()
        binding.finished.isChecked = goal.isDone
        binding.goalDescription.setText(goal.description)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            val mainActivity = this.activity as MainActivity
            if (binding.goalName.text.toString().isEmpty()){
                binding.editName.error = getString(R.string.error_empty_goal_name)
                return@setOnClickListener
            }

            val goal = appStorage.goals[position]
            goal.name = binding.goalName.text.toString()
            goal.deadline = binding.goalDeadline.text.toString()
            goal.priority = binding.editPriority.text.toString().toInt()
            goal.isDone = binding.finished.isChecked
            goal.description = binding.goalDescription.text.toString()
            appStorage.goals[position] = goal
            mainActivity.adapter.notifyItemChanged(position)
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
        fun newInstance(position:Int): EditGoalDialogFragment {
            val f = EditGoalDialogFragment()
            val args = Bundle()
            args.putInt("position", position)
            f.arguments = args
            return f
        }
    }
}