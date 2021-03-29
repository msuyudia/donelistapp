package com.suy.donelistapp.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suy.donelistapp.room.dao.ActivityEntityDao
import com.suy.donelistapp.room.dao.UserDao
import com.suy.donelistapp.room.entity.ActivityEntity
import com.suy.donelistapp.room.entity.User

@Database(entities = [User::class, ActivityEntity::class], version = 1)
abstract class DatabaseApp : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun activityDao(): ActivityEntityDao
}