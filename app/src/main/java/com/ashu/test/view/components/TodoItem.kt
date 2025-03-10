package com.ashu.test.view.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashu.test.R
import com.ashu.test.data.TodoModel
import com.ashu.test.viewModels.TodoViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(
    todo: TodoModel,
    todoViewModel: TodoViewModel
) {

//    val isMultiSelect = remember { mutableStateOf(false) }
    var isMultiSelect = todoViewModel.selectedTodos.contains(todo)
    println("isMultiSelect: $isMultiSelect")

    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.customGrey),
        ),
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .border(
                width = 4.dp,
                color = if (isMultiSelect) colorResource(R.color.customOrange) else Color.Transparent
            )
            .combinedClickable(
                onClick = {
                    println("CLICKED___")

                    if (!todoViewModel.isMultiSelectFeatureActive.value) {
                        todoViewModel.from.value = "update"
                        todoViewModel.titleField.value = todo.title
                        todoViewModel.descriptionField.value = todo.description
                        todoViewModel.todoDateState.value = LocalDate.parse(todo.todoDate)
                        todoViewModel.todoId = todo.id
                        todoViewModel.createdDate.value = todo.createdDate
                        todoViewModel.todoTimeState.value = LocalTime.parse(todo.todoTiming)
                    }
                    if (todoViewModel.isMultiSelectFeatureActive.value) {
                        isMultiSelect = !isMultiSelect
                    }
                    if (isMultiSelect) {
                        todoViewModel.selectedTodos.add(todo)
                    } else {
                        todoViewModel.selectedTodos.remove(todo)
                    }
                    if (todoViewModel.selectedTodos.isEmpty()) {
                        todoViewModel.isMultiSelectFeatureActive.value = false
                    }
                },
                onLongClick = {
                    todoViewModel.isMultiSelectFeatureActive.value = true
                    if (isMultiSelect) {
                        todoViewModel.selectedTodos.remove(todo)
                    } else {
                        todoViewModel.selectedTodos.add(todo)
                    }
                    isMultiSelect = !isMultiSelect
                }
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = todo.title.capitalize(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = todo.description.capitalize(),
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Todo date: " + todo.todoDate.format(todoViewModel.formattedDate), color = Color.White
                )
                if(todo.todoTiming.isNotEmpty()) {
                    Text(
                        text = "Todo time: " + LocalTime.parse(todo.todoTiming).format(todoViewModel.formattedTime),
                        color = Color.White
                    )
                }
                if(todo.createdDate.isNotEmpty())
                Text(
                    text = "Created date: " + LocalDate.parse(todo.createdDate).format(todoViewModel.formattedDate) ,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
            }
            Checkbox(

                checked = todo.isCompleted,
                onCheckedChange = {
                    todoViewModel.updateTodo(todo.copy(isCompleted = it))
                },

                )
        }

    }
}