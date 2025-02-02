package com.ashu.test.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashu.test.NotificationHelper
import com.ashu.test.R
import com.ashu.test.data.TodoModel
import com.ashu.test.viewModels.TodoViewModel
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTodoDialog(
    onDismissRequest: () -> Unit,
    onSave: (title: String, description: String, todoDate: LocalDate, todoTime: LocalTime) -> Unit,
    todoViewModel: TodoViewModel,
    notificationHelper: NotificationHelper
) {


    BasicAlertDialog(

        onDismissRequest = { onDismissRequest() },
        modifier = Modifier
            .wrapContentSize()
            .background(colorResource(R.color.customGrey)),
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Gray, unfocusedTextColor = Color.Gray
                    ),

                    onValueChange = {
                        todoViewModel.titleField.value = it
                        todoViewModel.titleFieldError.value = null
                    },
                    value = todoViewModel.titleField.value,
                    label = { Text("Title") },
                    isError = todoViewModel.titleFieldError.value != null,
                )

                Spacer(modifier = Modifier.height(4.dp))
                if (todoViewModel.titleFieldError.value != null) {
                    Text(
                        text = "Title is required",
                        color = Color.Red,
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontSize = 12.sp
                        )
                    )
                }

                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.Gray, unfocusedTextColor = Color.Gray
                    ),
                    onValueChange = {
                        todoViewModel.descriptionField.value = it
                        todoViewModel.descriptionFieldError.value = null
                    },
                    value = todoViewModel.descriptionField.value,
                    label = { Text("Description") },
                    isError = todoViewModel.descriptionFieldError.value != null,
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (todoViewModel.descriptionFieldError.value != null) {
                    Text(
                        text = "Description is required",
                        color = Color.Red,
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontSize = 12.sp
                        )
                    )
                }
                Box(
                    modifier = Modifier.padding(
                        top = 10.dp, end = 6.dp
                    )
                ) {
                    DatePickerTextField(selectedDate = todoViewModel.todoDateState.value,
                        onDateSelected = { selectedDate ->
                            todoViewModel.todoDateState.value = selectedDate
                        })
                }
                Box(
                    modifier = Modifier.padding(
                        top = 10.dp, end = 6.dp
                    )
                ) {
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

                Spacer(modifier = Modifier.height(24.dp))
                Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = {
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
                        todoViewModel.showDialog.value = false
                    } else {
                        var isValid = true
                        if (todoViewModel.titleField.value.isEmpty()) {
                            todoViewModel.titleFieldError.value = "Title is required"
                            isValid = false
                        } else {
                            todoViewModel.titleFieldError.value = null
                        }
                        if (todoViewModel.descriptionField.value.isEmpty()) {
                            todoViewModel.descriptionFieldError.value = "Description is required"
                            isValid = false
                        } else {
                            todoViewModel.descriptionFieldError.value = null
                        }
                        if (isValid) {
                            onSave(
                                todoViewModel.titleField.value,
                                todoViewModel.descriptionField.value,
                                todoViewModel.todoDateState.value,
                                todoViewModel.todoTimeState.value
                            )
                        }
                    }
                    todoViewModel.clearValues()
                }) {
                    Text(text = if (todoViewModel.from.value == "update") "Edit" else "Save")
                }


            }
        })
}

