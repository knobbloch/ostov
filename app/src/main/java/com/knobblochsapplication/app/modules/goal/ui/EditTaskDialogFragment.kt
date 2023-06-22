package com.knobblochsapplication.app.modules.goal.ui

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.databinding.FragmentEditTaskBinding
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class EditTaskDialogFragment : DialogFragment() {
    lateinit var binding: FragmentEditTaskBinding
    private val appStorage: AppStorage by inject()
    var taskUid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for fullscreen
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setStyle(STYLE_NO_TITLE, R.style.fullscreendialog)
        taskUid = arguments?.getString("taskUid")
        //binding = EditTaskDialogFragment.inflate(layoutInflater)

        createNotificationChannel()

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditTaskBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnSave.setOnClickListener {
            var goalActivity = this.activity as GoalActivity
            if (binding.goalName.text.toString().isEmpty()) {
                binding.editName.error = getString(R.string.error_empty_goal_name)
                return@setOnClickListener
            }
            scheduleNotification()
            createNotificationChannel()
            appStorage.changeTask(
                (activity as GoalActivity).lastSelectedGoalUid,
                taskUid!!,
                binding.goalName.text.toString(),
                binding.goalDeadline.text.toString(),
                binding.finished.isChecked,
                binding.editPriority.text.toString().toInt(),
                binding.goalDescription.text.toString()
            )
            goalActivity.updateView()
            dismiss()

        }
//        binding.editDate.setEndIconOnClickListener {
//            binding.editDate.editText?.setText("")
//        }
//        binding.editDate.setStartIconOnClickListener {
//            showDatePicker()
//        }
//        binding.goalDeadline.setOnClickListener() {
//            showDatePicker()
//        }
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
        val goal = appStorage.getGoalByUid((activity as GoalActivity).lastSelectedGoalUid)
        if (goal == null || taskUid == null) {
            return
        }
        val task = goal.getTaskByUid(taskUid!!)
        if (task != null) {
            binding.goalDeadline.setText(task.deadline)
            binding.editPriority.text = task.priority.toString()
            binding.finished.isChecked = task.isDone
            binding.goalName.setText(task.name)
            binding.goalDescription.setText(task.description)
        }

    }

//    fun showDatePicker() {
//        val constraintsBuilder =
//            CalendarConstraints.Builder()
//                .setFirstDayOfWeek(Calendar.MONDAY)
//        val datePicker =
//            MaterialDatePicker.Builder.datePicker()
//                .setTitleText(getString(R.string.lbl17))
//                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                .setCalendarConstraints(constraintsBuilder.build())
//                .build()
//
//        datePicker.show(this.parentFragmentManager, null)
//        datePicker.addOnPositiveButtonClickListener {
//            val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
//            binding.editDate.editText?.setText(outputDateFormat.format(it))
//        }
//    }
private fun scheduleNotification()
{
    val intent = Intent(activity?.applicationContext, Notification::class.java)

    val pendingIntent = PendingIntent.getBroadcast(
        activity?.applicationContext,
        notificationID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val time = getTime()
    times.add(time)
    times.sort()
    if (!notif){
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            times[0],
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}


    private fun getTime(): Long
    {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel1", name, importance)
        channel.description = desc

        val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    companion object {
        fun newInstance(taskUid: String): EditTaskDialogFragment {
            val f = EditTaskDialogFragment()
            val args = Bundle()
            args.putString("taskUid", taskUid)
            f.arguments = args
            return f
        }
    }
}