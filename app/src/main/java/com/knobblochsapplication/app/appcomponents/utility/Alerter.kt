package com.knobblochsapplication.app.appcomponents.utility

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.knobblochsapplication.app.R
import com.knobblochsapplication.app.appcomponents.di.MyApp
import java.util.*
import java.util.concurrent.TimeUnit

object Alerter {

    fun initialize(context: Context) {
        //todo change interval - 1, TimeUnit.DAYS
        val workRequest = PeriodicWorkRequestBuilder<AlertWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "alerter",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }

    class AlertWorker(val context: Context, params: WorkerParameters) :
        Worker(context, params) {

        override fun doWork(): Result {
            val storage = (context.applicationContext as MyApp).st
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
                val builder = NotificationCompat.Builder(context, "agxnsyau3628")
                    .setContentTitle("Дедлайн сегодня")
                    .setContentText(item.name)
                    .setSmallIcon(R.drawable.img_navmenu)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    notificationManagerCompat.notify(Random().nextInt(), builder.build())
                }
            }
            return Result.success()
        }
    }
}