package com.knobblochsapplication.app.modules.goal.ui

import android.app.*
import android.os.Build
import android.app.AlarmManager
import android.app.AlarmManager.INTERVAL_DAY
import android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES
//import android.app.NotificationChannel
//import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.utility.AppStorage
import com.knobblochsapplication.app.databinding.FragmentCreateTaskBinding
import org.koin.android.ext.android.inject
import java.util.*

class CreateTaskDialogFragment : DialogFragment() {
    lateinit var binding: FragmentCreateTaskBinding
    private val appStorage: AppStorage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setStyle(STYLE_NO_TITLE, R.style.fullscreendialog)

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
            createNotificationChannel()
            scheduleNotification()
            appStorage.addTask(
                goalActivity.lastSelectedGoalUid,
                binding.goalName.text.toString(),
                binding.goalDeadline.text.toString(),
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
    }

//    @SuppressLint("RestrictedApi")
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
//        if (Notification_permision.hasPermissions(activity?.applicationContext)){

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
                INTERVAL_DAY,
                pendingIntent
            )
        }
//        }
//        else{
//            Notification_permision.requestPermissions(activity?.applicationContext as Activity, 102)
//        }
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

    companion object {
        fun newInstance(): CreateTaskDialogFragment {
            val f = CreateTaskDialogFragment()

            return f
        }
    }
    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


}
