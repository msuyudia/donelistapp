package com.suy.donelistapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.donelistapp.R
import com.suy.donelistapp.room.database.DatabaseManager
import com.suy.donelistapp.sharedpreference.PrefManager
import com.suy.donelistapp.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val mutableLiveData by lazy { MutableLiveData<Resource<String>>() }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            mutableLiveData.postValue(Resource.loading())
            val user = DatabaseManager.db.userDao().login(email, password)
            when (user != null) {
                true -> {
                    PrefManager.saveUserSession(user)
                    mutableLiveData.postValue(Resource.success(user.name))
                }
                false -> mutableLiveData.postValue(Resource.error(R.string.text_wrong_email_or_password))
            }
        }
    }

    fun loginLiveData(): LiveData<Resource<String>> = mutableLiveData
}
