package com.ashu.test

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class TaskWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (appWidgetId in appWidgetIds!!) {
            val views = RemoteViews(context?.packageName, R.layout.widget_layout)
            val add_task_intent = Intent(context, MainActivity::class.java)
            val add_task_pending_intent =
                PendingIntent.getActivity(context, 0, add_task_intent, PendingIntent.FLAG_IMMUTABLE)

            views.setOnClickPendingIntent(R.id.widget_title_list,add_task_pending_intent)
            val taskList = context?.let { getUpcomingTaskList(it) }
            views.setTextViewText(R.id.widget_title_list,taskList)
            appWidgetManager?.updateAppWidget(appWidgetId,views)
        }
    }
    private fun getUpcomingTaskList(context: Context): String {
        return "Task 1 - Tomorrow\nTask 2 - Next Week" // Replace with real task data from a ViewModel or repository
    }
}