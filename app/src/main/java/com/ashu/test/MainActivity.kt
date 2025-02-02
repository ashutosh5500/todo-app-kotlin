package com.ashu.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ashu.test.ui.theme.MyOwnTodoAppTheme
import com.ashu.test.view.MainScreen
import com.ashu.test.viewModels.TodoViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannel()
        notificationHelper.requestNotificationPermission(this)

        setContent {
            val todoViewModel: TodoViewModel = viewModel()
            MyOwnTodoAppTheme {
                MainScreen(todoViewModel, notificationHelper)
            }
        }
    }
}


/* IDEAS */

//Your to-do app already has a solid foundation with these essential features! Here are some ideas for additional features that could make it stand out even more:
//
//Advanced Feature Ideas
//Notifications & Reminders: Allow users to set reminders for specific tasks with custom notification times.
//Recurring Tasks: Enable tasks to repeat daily, weekly, monthly, or on custom intervals.
//Categories & Tags: Let users organize tasks by categories or tags for better organization and filtering.
//Priority Levels: Add priority indicators (e.g., high, medium, low) to help users focus on important tasks.
//Dark Mode: Implement a dark mode option to enhance the user experience.
//Backup & Sync: Allow data backup to cloud storage (e.g., Google Drive) or sync tasks across multiple devices.
//Widgets: Add home screen widgets to display upcoming tasks and quick actions.
//Analytics: Provide insights or analytics, such as completed tasks over time, to help users track their productivity.
//UI Enhancements
//Animations: Add subtle animations when creating, updating, or deleting tasks to make the app feel more interactive.
//Custom Themes: Let users choose color themes or backgrounds to personalize the app.
//Publishing this app with unique touches could help it stand out even in a crowded category. Let me know if you'd like guidance on any specific feature!