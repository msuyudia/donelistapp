package com.suy.donelistapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.donelistapp.room.database.DatabaseManager
import com.suy.donelistapp.room.entity.ActivityEntity
import com.suy.donelistapp.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val showAllActivity by lazy { MutableLiveData<Resource<List<ActivityEntity>>>() }

    fun getAllActivity(email: String) {
        viewModelScope.launch {
            showAllActivity.postValue(Resource.loading())
            val allActivity = DatabaseManager.db.activityDao().getAllActivity(email)
            when (allActivity.isNullOrEmpty()) {
                true -> showAllActivity.postValue(Resource.success(mutableListOf()))
                false -> showAllActivity.postValue(Resource.success(allActivity))
            }
        }
    }

    fun activitiesLiveData(): LiveData<Resource<List<ActivityEntity>>> = showAllActivity
}
