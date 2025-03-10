package com.ashu.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ashu.test.navigation.SetupNavGraph
import com.ashu.test.ui.theme.MyOwnTodoAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannel()
        notificationHelper.requestNotificationPermission(this)

        setContent {
            MyOwnTodoAppTheme {
                SetupNavGraph(
                    notificationHelper = notificationHelper
                )
            }
        }
    }

}
