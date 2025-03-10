package com.ashu.test

import android.content.Context
import androidx.room.Room
import com.ashu.test.data.TodoDB
import com.ashu.test.data.TodoRepository

object Graph {
    private lateinit var db: TodoDB
    val todoRepository by lazy {
        TodoRepository(
            todoDao = db.todoDao()
        )
    }

    fun provide(context: Context) {
        db = Room.databaseBuilder(context, TodoDB::class.java, "todo.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}