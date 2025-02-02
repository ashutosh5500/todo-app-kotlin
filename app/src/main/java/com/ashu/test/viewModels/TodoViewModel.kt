package com.ashu.test.viewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashu.test.Graph
import com.ashu.test.data.TodoModel
import com.ashu.test.data.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class TodoViewModel(
    private val todoRepository: TodoRepository = Graph.todoRepository
) : ViewModel() {
    val formattedDate = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val formattedTime = DateTimeFormatter.ofPattern("hh:mm")
    val showDialog = mutableStateOf(false)
    val from = mutableStateOf("")
    val usernames = listOf("All", "Personal", "Work")
    val itemPosition = mutableIntStateOf(0)
    val isExpanded = mutableStateOf(false)
    val isMultiSelectFeatureActive = mutableStateOf(false)
    val isSearchActive = mutableStateOf(false)
    val selectedTodos = mutableStateListOf<TodoModel>()
    var todoId: UUID = UUID.randomUUID()

    val titleField = mutableStateOf("")
    val descriptionField = mutableStateOf("")
    val titleFieldError = mutableStateOf<String?>(null)
    val descriptionFieldError = mutableStateOf<String?>(null)
    val createdDate = mutableStateOf("")

    val selectedDateField = mutableStateOf(LocalDate.now())
    val todoDateState = mutableStateOf<LocalDate>(LocalDate.now())
    val todoTimeState = mutableStateOf<LocalTime>(LocalTime.now())
    lateinit var getAllTodos: Flow<List<TodoModel>>
    val searchValue = mutableStateOf("")

    // Initialize the getAllTodos property when the ViewModel is created
    init {
        viewModelScope.launch {
            getAllTodos = todoRepository.getAllTodos()
        }
    }

    // Other ViewModel methods...
    fun addTodo(todo: TodoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.addTodo(todo)
        }
    }

    fun deleteTodo(todo: TodoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.deleteTodo(todo)
        }
    }

    fun updateTodo(todo: TodoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.updateTodo(todo)
        }
    }

    fun clearValues() {
        titleField.value = ""
        descriptionField.value = ""
        todoDateState.value = LocalDate.now()
        todoTimeState.value = LocalTime.now()
    }
}



