package com.suy.donelistapp.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val email: String,
    val title: String,
    val description: String
)
