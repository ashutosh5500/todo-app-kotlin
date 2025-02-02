package com.ashu.test.data

import kotlinx.coroutines.flow.Flow
import java.util.UUID

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllTodos(): Flow<List<TodoModel>> {
        return todoDao.getAllTodos()
    }

    fun getTodoById(id: UUID): TodoModel? {
        return todoDao.getTodoById(id)
    }

    fun updateTodo(todo: TodoModel) {
        todoDao.updateTodo(todo)
    }

    fun deleteTodo(todo: TodoModel) {
        todoDao.deleteTodo(todo)
    }

    fun addTodo(todo: TodoModel) {
        todoDao.addTodo(todo)
    }
}