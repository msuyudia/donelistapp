package com.suy.donelistapp.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.suy.donelistapp.room.entity.User

object PrefManager {
    private lateinit var pref: SharedPreferences
    private const val PREF_NAME = "DoneListApp"
    private const val USER_SESSION = "UserSession"
    private val gson by lazy { Gson() }

    fun initiate(context: Context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getUserSession(): User? = gson.fromJson(pref.getString(USER_SESSION, ""), User::class.java)

    fun saveUserSession(user: User) = pref.edit().putString(USER_SESSION, gson.toJson(user)).apply()

    fun clearSession() = pref.edit().clear().apply()
}