package com.ashu.test.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
abstract class TodoDao {
    @Query("SELECT * FROM todos_table")
    abstract fun getAllTodos(): Flow<List<TodoModel>>

    @Query("SELECT * FROM todos_table WHERE id = :id")
    abstract fun getTodoById(id: UUID): TodoModel?

    @Update
    abstract fun updateTodo(todo: TodoModel)

    @Delete
    abstract fun deleteTodo(todo: TodoModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun addTodo(todo: TodoModel)


}
