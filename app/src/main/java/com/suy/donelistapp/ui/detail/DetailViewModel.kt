package com.suy.donelistapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suy.donelistapp.R
import com.suy.donelistapp.room.database.DatabaseManager
import com.suy.donelistapp.room.entity.ActivityEntity
import com.suy.donelistapp.utils.Resource
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val detail by lazy { MutableLiveData<Resource<ActivityEntity>>() }
    private val delete by lazy { MutableLiveData<Resource<Int>>() }

    fun getDetailActivity(id: Int) {
        viewModelScope.launch {
            detail.postValue(Resource.loading())
            val activity = DatabaseManager.db.activityDao().getActivityEntity(id)
            when (activity != null) {
                true -> detail.postValue(Resource.success(activity))
                false -> detail.postValue(Resource.error(R.string.text_activity_error))
            }
        }
    }

    fun deleteActivity(activityEntity: ActivityEntity?) {
        viewModelScope.launch {
            delete.postValue(Resource.loading())
            when (activityEntity != null) {
                true -> {
                    val deleted = DatabaseManager.db.activityDao().deleteActivity(activityEntity)
                    when (deleted > 0) {
                        true -> delete.postValue(Resource.success(R.string.text_activity_success_deleted))
                        false -> delete.postValue(Resource.error(R.string.text_delete_activity_failed))
                    }
                }
                false -> delete.postValue(Resource.error(R.string.text_delete_activity_failed))
            }
        }
    }

    fun detailLiveData(): LiveData<Resource<ActivityEntity>> = detail
    fun deleteLiveData(): LiveData<Resource<Int>> = delete
}
