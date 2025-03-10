package com.ashu.test.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ashu.test.NotificationHelper
import com.ashu.test.R
import com.ashu.test.navigation.Screen
import com.ashu.test.view.components.TitleComponent
import com.ashu.test.view.components.TodoAppBars
import com.ashu.test.view.components.TodoItem
import com.ashu.test.viewModels.TodoViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import java.time.LocalDate
import java.time.Period

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MainScreen(
    todoViewModel: TodoViewModel,
    notificationHelper: NotificationHelper,
    navController: NavHostController
) {


    val todoAppBars = TodoAppBars()
    Scaffold(topBar = {
        if (todoViewModel.isMultiSelectFeatureActive.value) {
            todoAppBars.DeleteAppBar(todoViewModel)
        } else if (todoViewModel.isSearchActive.value) {
            todoAppBars.SearchAppBar(todoViewModel)
        } else {
            todoAppBars.DefaultAppBar(todoViewModel)
        }

    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                todoViewModel.from.value = ""
                navController.navigate(Screen.AddOrUpdate.route);
            }, containerColor = colorResource(R.color.customOrange)
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White
            )
        }
    }, containerColor = Color(0xFF222222)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(32.dp))
            val allTodos = todoViewModel.getAllTodos.collectAsState(initial = emptyList())
            val today = LocalDate.now()
            /// instead of condition by 2 to 7 days use upcoming sunday as this week condition
            val endOfWeek = today.with(java.time.DayOfWeek.SUNDAY)
            val tomorrow = today.plusDays(1)
            val pastTodo = allTodos.value.filter {
                LocalDate.parse(it.todoDate) < today
            }
            val todayTodo = allTodos.value.filter {
                LocalDate.parse(it.todoDate) == today
            }
            val tomorrowTodo = allTodos.value.filter {
                LocalDate.parse(it.todoDate) == tomorrow
            }
            var thisWeek = allTodos.value.filter { todo ->
                val todoDate = LocalDate.parse(todo.todoDate)
                todoDate.isAfter(tomorrow) && todoDate.isBefore(endOfWeek.plusDays(1))
            }
            var nextWeek = allTodos.value.filter { todo ->
                val todoDate = LocalDate.parse(todo.todoDate)
                todoDate.isAfter(tomorrow) && todoDate.isAfter(endOfWeek)
            }
            var upcomingDays = allTodos.value.filter { todo ->
                val todoDate = LocalDate.parse(todo.todoDate)
                val period = Period.between(today, todoDate)
                period.days >= 15
            }


            val searchTodoList = allTodos.value.filter {
                it.title.lowercase().contains(todoViewModel.searchValue.value.lowercase())
            }

            thisWeek = thisWeek.sortedBy { LocalDate.parse(it.todoDate) }
            nextWeek = nextWeek.sortedBy { LocalDate.parse(it.todoDate) }
            upcomingDays = upcomingDays.sortedBy { LocalDate.parse(it.todoDate) }
            println("allTodos.value+++${allTodos.value}")
            if (allTodos.value.isEmpty()) {
                Text("No Todos")
            }

            LazyColumn {
                if (todoViewModel.searchValue.value.isNotEmpty()) {
                    items(searchTodoList.size) {
                        TodoItem(searchTodoList[it], todoViewModel)
                    }
                }
            }
            if (todoViewModel.searchValue.value.isEmpty()) LazyColumn {
                if (pastTodo.isNotEmpty()) {
                    item {
                        TitleComponent("Past")
                    }
                    items(pastTodo.size) {
                        TodoItem(pastTodo[it], todoViewModel)
                    }
                }
                if (todayTodo.isNotEmpty()) {
                    item {
                        TitleComponent("Today")

                    }
                    items(todayTodo.size) {
                        TodoItem(todayTodo[it], todoViewModel)
                    }
                }

                if (tomorrowTodo.isNotEmpty()) {
                    item {
                        TitleComponent("Tomorrow")

                    }
                    items(tomorrowTodo.size) {
                        TodoItem(tomorrowTodo[it], todoViewModel)
                    }
                }
                if (thisWeek.isNotEmpty()) {
                    item {
                        TitleComponent("This week")
                    }
                    items(thisWeek.size) {
                        TodoItem(thisWeek[it], todoViewModel)
                    }
                }
                if (nextWeek.isNotEmpty()) {
                    item {
                        TitleComponent("Next week")

                    }
                    items(nextWeek.size) {
                        TodoItem(nextWeek[it], todoViewModel)
                    }
                }
                if (upcomingDays.isNotEmpty()) {
                    item {
                        TitleComponent("Upcoming days")

                    }
                    items(upcomingDays.size) {
                        TodoItem(upcomingDays[it], todoViewModel)
                    }
                }
            }
        }
    }
}





