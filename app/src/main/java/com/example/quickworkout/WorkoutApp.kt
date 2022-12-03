package com.example.quickworkout

import android.app.Application

// create the application class
class WorkOutApp: Application() {

    val db:HistoryDatabase by lazy {
        HistoryDatabase.getInstance(this)
    }
}
