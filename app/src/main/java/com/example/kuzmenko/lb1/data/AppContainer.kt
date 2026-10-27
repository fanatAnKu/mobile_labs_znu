package com.example.kuzmenko.lb1.data

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.kuzmenko.lb1.data.local.AppDatabase

class AppContainer(
    context: Context
) {

    private val database =
        Room.databaseBuilder<AppDatabase>(
            context.applicationContext,
            name = "book_tracker.db"
        )
            .setDriver(
                AndroidSQLiteDriver()
            )
            .build()

    val bookRepository =
        BookRepository(
            bookDao =
                database.bookDao()
        )
}