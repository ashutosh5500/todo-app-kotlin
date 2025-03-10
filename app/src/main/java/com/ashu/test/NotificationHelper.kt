package com.ashu.test

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import com.ashu.test.data.TodoModel
import java.util.concurrent.TimeUnit

class NotificationHelper(
    private val context: Context
) {
    companion object {
        const val CHANNEL_ID = "TaskReminderChannel"
        const val NOTIFICATION_PERMISSION_CODE = 1001
//        const val NOTIFICATION_ID = 1
    }

    fun createNotificationChannel() {
        val name: String = "Task Reminder Channel"
        val descriptionText = "Channel for task reminders"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }



    fun showNotification(title: String, message: String) {
        try {
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app’s icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // High importance
                .setAutoCancel(true).build() // Automatically dismiss when tapped

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(
                System.currentTimeMillis().toInt(),
                notification,
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun requestNotificationPermission(activity: ComponentActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }
    }

    private fun scheduleTaskReminder(
        taskTitle: String,
        taskDescription: String,
        delayInMillis: Long
    ) {
        val data = Data.Builder().putString("taskTitle", taskTitle)
            .putString("taskDescription", taskDescription).build()
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>().setInputData(inputData = data)
            .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS).build()

    }
    fun setTaskReminder(task: TodoModel) {
        task.todoTiming.let { todoTiming ->
            scheduleTaskReminder(task.title, "Reminder for your task: ${task.title}", 100)
        }
    }
}
