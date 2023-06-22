package com.knobblochsapplication.app.modules.goal.ui

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.knobblochsapplication.app.R
import java.util.Date

var notif = false
var times = mutableListOf<Long>()
var notificationID = 1
const val channelID = "channel1"
const val messageExtra = "Дедлайны задач всё ближе!!!"

class Notification: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent)
    {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_settings)
            .setContentTitle("Грамотно выбирайте темп работы!")
            .setContentText(messageExtra)
            .build()
        val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notif = true
        times.removeFirst()
        manager.notify(notificationID++, notification)
    }
}