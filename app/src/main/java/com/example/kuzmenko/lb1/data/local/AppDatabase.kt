package com.example.kuzmenko.lb1.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase


@Database(
    entities = [
        BookEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase :
    RoomDatabase() {

    abstract fun bookDao():
            BookDao
}