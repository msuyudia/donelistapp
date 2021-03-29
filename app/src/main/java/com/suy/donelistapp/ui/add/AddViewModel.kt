package com.suy.donelistapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.donelistapp.R
import com.suy.donelistapp.room.database.DatabaseManager
import com.suy.donelistapp.room.entity.ActivityEntity
import com.suy.donelistapp.utils.Resource
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {
    private val mutableLiveData by lazy { MutableLiveData<Resource<Int>>() }

    fun saveActivity(email: String?, title: String, description: String) {
        viewModelScope.launch {
            mutableLiveData.postValue(Resource.loading())
            val activityEntity =
                ActivityEntity(email = email ?: "", title = title, description = description)
            val saveActivity = DatabaseManager.db.activityDao().saveActivity(activityEntity)
            when (saveActivity > 0) {
                true -> mutableLiveData.postValue(Resource.success(R.string.text_activity_success_added))
                false -> mutableLiveData.postValue(Resource.error(R.string.text_add_activity_failed))
            }
        }
    }

    fun addedLiveData(): LiveData<Resource<Int>> = mutableLiveData
}
