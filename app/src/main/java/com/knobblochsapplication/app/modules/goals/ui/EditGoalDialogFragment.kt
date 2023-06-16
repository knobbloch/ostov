package com.knobblochsapplication.app.modules.goals.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.databinding.FragmentEditGoalBinding
import java.text.SimpleDateFormat
import java.util.*

class EditGoalDialogFragment : DialogFragment() {
    lateinit var binding: FragmentEditGoalBinding
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditGoalBinding.inflate(layoutInflater)
        /*var goalsActivity:GoalsActivity = this.activity as GoalsActivity
        var goal = goalsActivity.goalsList[position]
        binding.goalName.setText(goal.goalName)
        binding.goalDeadline.setText(goal.goalDeadline)
        binding.editPriority.text = goal.goalPriority.toString()
        binding.finished.isChecked = goal.goalIsDone
        binding.goalDescription.setText(goal.goalDescription)*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            var goalsActivity = this.activity as GoalsActivity
            if (binding.goalName.text.toString().isEmpty()){
                binding.editName.error = getString(R.string.error_empty_goal_name)
                return@setOnClickListener
            }
            val goal = Goal(
                binding.goalName.text.toString(),
                binding.goalDeadline.text.toString(),
                binding.editPriority.text.toString().toInt(),
                binding.finished.isChecked,
                binding.goalDescription.text.toString()
            )
            /*goalsActivity.goalsList[position] = goal
            goalsActivity.adapter.notifyItemChanged(position)
            dismiss()*/
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