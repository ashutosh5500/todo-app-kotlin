package com.ashu.test

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

abstract class NotificationWorker(
    context: Context,
    params: WorkerParameters,
    private val notificationHelper: NotificationHelper
) : Worker(context, params) {
    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Task reminder"
        val message = inputData.getString("message") ?: "You have a task to complete"
        notificationHelper.showNotification(title, message)
        return Result.success()
    }
}