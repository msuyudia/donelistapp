package com.suy.donelistapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.suy.donelistapp.room.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): User?

    @Insert
    suspend fun register(user: User): Long
}