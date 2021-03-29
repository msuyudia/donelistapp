package com.suy.donelistapp.room.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room

@SuppressLint("StaticFieldLeak")
object DatabaseManager {
    private lateinit var context: Context
    private const val dbName = "DoneList.db"

    fun initiate(context: Context) {
        this.context = context
    }

    val db by lazy { Room.databaseBuilder(context, DatabaseApp::class.java, dbName).build() }
}