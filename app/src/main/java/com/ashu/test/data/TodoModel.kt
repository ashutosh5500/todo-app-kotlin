package com.ashu.test.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "todos_table")
data class TodoModel(
    @PrimaryKey(autoGenerate = false)
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "todo_date")
    val todoDate: String = "",
    @ColumnInfo(name = "created_date")
    val createdDate: String = "",
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "todo_timing")
    val todoTiming: String = ""
)
