package com.example.kuzmenko.lb1

import android.app.Application
import com.example.kuzmenko.lb1.data.AppContainer

class BookTrackerApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container =
            AppContainer(
                applicationContext
            )
    }
}