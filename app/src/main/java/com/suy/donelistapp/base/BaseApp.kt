package com.suy.donelistapp.base

import android.app.Application
import com.suy.donelistapp.room.database.DatabaseManager
import com.suy.donelistapp.sharedpreference.PrefManager

class BaseApp : Application() {
    override fun onCreate() {
        PrefManager.initiate(this)
        DatabaseManager.initiate(this)
        super.onCreate()
    }
}