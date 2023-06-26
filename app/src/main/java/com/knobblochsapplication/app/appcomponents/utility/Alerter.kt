package com.knobblochsapplication.app.appcomponents.utility

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import com.knobblochsapplication.app.modules.main.ui.MainActivity
import java.util.*
import java.util.concurrent.TimeUnit


object Alerter {

    fun initialize(context: Context) {
        //todo change interval - 1, TimeUnit.DAYS
        val workRequest = PeriodicWorkRequestBuilder<AlertWorker>(1, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "alerter",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    class AlertWorker(val context: Context, params: WorkerParameters) :
        Worker(context, params) {

        override fun doWork(): Result {
            val storage = (context.applicationContext as MyApp).st
            val preferenceHelper = (context.applicationContext as MyApp).pr
            val list = storage.getDeadlineToday()
            for (item in list) {
                val notificationManagerCompat: NotificationManagerCompat =
                    NotificationManagerCompat.from(context)
                notificationManagerCompat.createNotificationChannel(
                    NotificationChannel(
                        "agxnsyau3628",
                        "Deadline alerts",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
                println("!!!" + item.name)

                val resultIntent = Intent(context, MainActivity::class.java)
                val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                    addNextIntentWithParentStack(resultIntent)
                    getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                }

                val builder = NotificationCompat.Builder(context, "agxnsyau3628")
                    .setContentTitle("Дедлайн сегодня")
                    .setContentText(item.name)
                    .setSmallIcon(R.drawable.img_navmenu)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true) //автоматически закрыть уведомление после нажатия
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED && preferenceHelper.getIsNotificationEnabled()
                ) {
                    notificationManagerCompat.notify(Random().nextInt(), builder.build())
                }
            }
            return Result.success()
        }
    }
}