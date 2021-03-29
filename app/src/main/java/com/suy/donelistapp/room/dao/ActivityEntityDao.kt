package com.suy.donelistapp.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.suy.donelistapp.room.entity.ActivityEntity

@Dao
interface ActivityEntityDao {
    @Insert
    suspend fun saveActivity(activityEntity: ActivityEntity): Long

    @Query("SELECT * FROM activityentity WHERE email = :email")
    suspend fun getAllActivity(email: String): List<ActivityEntity>?

    @Query("SELECT * FROM activityentity WHERE id = :id")
    suspend fun getActivityEntity(id: Int): ActivityEntity?

    @Delete
    suspend fun deleteActivity(activityEntity: ActivityEntity): Int
}