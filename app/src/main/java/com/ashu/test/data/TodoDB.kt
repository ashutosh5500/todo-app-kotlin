package com.ashu.test.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoModel::class], version = 3)
abstract class TodoDB: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}