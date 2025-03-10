package com.ashu.test.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashu.test.NotificationHelper
import com.ashu.test.view.AddOrUpdateScreen
import com.ashu.test.view.MainScreen
import com.ashu.test.viewModels.TodoViewModel

@Composable
fun SetupNavGraph(todoViewModel: TodoViewModel = viewModel(), notificationHelper: NotificationHelper, navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController, todoViewModel = todoViewModel, notificationHelper = notificationHelper)
        }
        composable(route = Screen.AddOrUpdate.route) {
            AddOrUpdateScreen(navController = navController, todoViewModel = todoViewModel, notificationHelper = notificationHelper)
        }
    }
}