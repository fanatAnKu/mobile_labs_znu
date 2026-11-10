package com.example.kuzmenko.lb1.data

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.kuzmenko.lb1.data.local.AppDatabase
import com.example.kuzmenko.lb1.data.remote.BookApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

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

    private val json = Json {
        ignoreUnknownKeys = true
    }
    companion object {
        private const val BASE_URL = "https://gutendex.com/"
    }

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(
                BASE_URL
            )
            .addConverterFactory(
                json.asConverterFactory(
                    contentType = "application/json".toMediaType()
                )
            )
            .build()

    private val bookApiService: BookApiService = retrofit.create(BookApiService::class.java)


    val bookRepository =
        BookRepository(
            bookDao = database.bookDao(),
            bookApiService = bookApiService
        )
}