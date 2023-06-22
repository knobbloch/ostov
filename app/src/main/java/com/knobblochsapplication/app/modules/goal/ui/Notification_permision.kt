package com.knobblochsapplication.app.modules.goal.ui
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class Notification_permision {

    companion object {
        fun hasPermissions(context: Context?): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager()
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                (ContextCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.SCHEDULE_EXACT_ALARM
                )
                        == PackageManager.PERMISSION_GRANTED)
            } else {
                true
            }
        }

        fun requestPermissions(activity: Activity, requestCode: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data = Uri.parse(String.format("package:%s", activity.packageName))
                    activity.startActivityForResult(intent, requestCode)
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    activity.startActivityForResult(intent, requestCode)
                }
            } else {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.SCHEDULE_EXACT_ALARM),
                    requestCode
                )
            }
        }
    }
}