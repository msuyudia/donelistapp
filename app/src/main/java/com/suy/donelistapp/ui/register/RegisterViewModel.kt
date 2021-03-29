package com.suy.donelistapp.ui.register

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.donelistapp.R
import com.suy.donelistapp.room.database.DatabaseManager
import com.suy.donelistapp.room.entity.User
import com.suy.donelistapp.sharedpreference.PrefManager
import com.suy.donelistapp.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val mutableLiveData by lazy { MutableLiveData<Resource<Int>>() }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            mutableLiveData.postValue(Resource.loading())
            val userDao = DatabaseManager.db.userDao()
            try {
                val user = User(email, name, password)
                val userRegistered = userDao.register(user)
                when (userRegistered > 0) {
                    true -> {
                        PrefManager.saveUserSession(user)
                        mutableLiveData.postValue(Resource.success(R.string.text_register_success))
                    }
                }
            } catch (e: SQLiteConstraintException) {
                mutableLiveData.postValue(Resource.error(R.string.text_account_already_registered))
            }
        }
    }

    fun registerLiveData(): LiveData<Resource<Int>> = mutableLiveData
}
