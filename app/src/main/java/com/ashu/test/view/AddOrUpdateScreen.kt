package com.ashu.test.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ashu.test.NotificationHelper
import com.ashu.test.R
import com.ashu.test.data.TodoModel
import com.ashu.test.view.components.CustomTextField
import com.ashu.test.view.components.DatePickerTextField
import com.ashu.test.view.components.TimePickerTextField
import com.ashu.test.view.components.TodoAppBars
import com.ashu.test.viewModels.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrUpdateScreen(
    todoViewModel: TodoViewModel,
    notificationHelper: NotificationHelper,
    navController: NavController
) {
    val todoAppBars = TodoAppBars()

    Scaffold(topBar = {
        todoAppBars.BasicAppBar(navController)
    }, containerColor = Color(0xFF222222), floatingActionButton = {
        FloatingActionButton(
            containerColor = colorResource(R.color.customOrange),

            onClick = {
                println("Save button clicked+++${todoViewModel.titleField.value} ${todoViewModel.descriptionField.value} ${todoViewModel.selectedDateField.value}")
                if (todoViewModel.titleField.value.isNotEmpty() && todoViewModel.descriptionField.value.isNotEmpty() && todoViewModel.from.value == "update") {
                    todoViewModel.updateTodo(
                        TodoModel(
                            id = todoViewModel.todoId,
                            title = todoViewModel.titleField.value,
                            description = todoViewModel.descriptionField.value,
                            todoDate = todoViewModel.todoDateState.value.toString(),
                            isCompleted = false,
                            createdDate = todoViewModel.createdDate.value,
                            todoTiming = todoViewModel.todoTimeState.value.toString()
                        )
                    )
                    navController.popBackStack()
                    todoViewModel.clearValues()
                } else {
                    var isValid = true
                    println("isValid1$isValid")
                    if (todoViewModel.titleField.value.isEmpty()) {
                        todoViewModel.titleFieldError.value = "Title is required"
                        isValid = false
                    } else {
                        todoViewModel.titleFieldError.value = null
                    }
                    println("isValid2$isValid")
                    if (todoViewModel.descriptionField.value.isEmpty()) {
                        todoViewModel.descriptionFieldError.value = "Description is required"
                        isValid = false
                    } else {
                        todoViewModel.descriptionFieldError.value = null
                    }
                    println("isValid3${todoViewModel.createdDate.value}----------${todoViewModel.titleField.value}-----${todoViewModel.descriptionField.value}------${todoViewModel.todoDateState.value}------${todoViewModel.todoTimeState.value}")
                    if (isValid) {
                        todoViewModel.addTodo(
                            TodoModel(
                                title = todoViewModel.titleField.value,
                                description = todoViewModel.descriptionField.value,
                                todoDate = todoViewModel.todoDateState.value.toString(),
                                isCompleted = false,
                                createdDate = todoViewModel.createdDate.value,
                                todoTiming = todoViewModel.todoTimeState.value.toString()
                            )
                        )
                        navController.popBackStack()
                        todoViewModel.clearValues()
                    }

                }

            }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Add",
                tint = Color.White
            )
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,

            ) {
            Spacer(modifier = Modifier.height(32.dp))
            CustomTextField(
                label = "What is to be done?",
                onChange = {
                    todoViewModel.titleField.value = it
                    todoViewModel.titleFieldError.value = null
                },
                value = todoViewModel.titleField.value,
                error = todoViewModel.titleFieldError.value
            )

            Spacer(modifier = Modifier.height(24.dp))
            CustomTextField(
                label = "Write something about it",
                onChange = {
                    todoViewModel.descriptionField.value = it
                    todoViewModel.descriptionFieldError.value = null
                },
                value = todoViewModel.descriptionField.value,
                error = todoViewModel.descriptionFieldError.value,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box {
                DatePickerTextField(
                    selectedDate = todoViewModel.todoDateState.value,
                    onDateSelected = { selectedDate ->
                        todoViewModel.todoDateState.value = selectedDate
                    })
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box() {
                TimePickerTextField(

                    selectedTime = todoViewModel.todoTimeState.value, onTimeSelected = {

                            selectedTime ->
                        todoViewModel.todoTimeState.value = selectedTime
                        val todo = TodoModel(
                            title = todoViewModel.titleField.value,
                            description = todoViewModel.descriptionField.value,
                            todoDate = todoViewModel.todoDateState.value.toString(),
                            todoTiming = todoViewModel.todoTimeState.value.toString()
                        )
                        notificationHelper.setTaskReminder(task = todo)
                    })
            }

        }
    }
}

