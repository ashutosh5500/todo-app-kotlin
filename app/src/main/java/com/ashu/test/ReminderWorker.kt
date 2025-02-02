package com.ashu.test

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

abstract class ReminderWorker(
    private val context: Context,
    params: WorkerParameters,
    private val notificationHelper: NotificationHelper
) : Worker(context, params) {
    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Task reminder"
        val message = inputData.getString("message") ?: "You have a task to complete"
//        showNotification(title, message)
        notificationHelper.showNotification(title, message)
        return Result.success()
    }

//    private fun showNotification(title: String, message: String) {
//        val builder = NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use your app's icon
//            .setContentTitle(title)
//            .setContentText(message)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setAutoCancel(true)
//
//        with(NotificationManagerCompat.from(context)) {
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
//            }
//            notify(NotificationHelper.NOTIFICATION_ID, builder.build())
//        }
//    }
}