package com.cocooncreations.topstories.ui.topstories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.cocooncreations.topstories.R
import com.cocooncreations.topstories.data.local.LocalDatabase
import com.cocooncreations.topstories.data.model.ResultEntity
import com.cocooncreations.topstories.remote.services.NetworkService
import com.cocooncreations.topstories.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class TopStoriesViewModel(private val networkService: NetworkService, private val context: Context) : ViewModel() {

    fun getTopStories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = networkService.getTopStories()))
        } catch (exception: Exception) {
            if (exception is UnknownHostException) {
                emit(
                        Resource.error(
                                data = null,
                                message = context.getString(R.string.no_internet_message))
                )
            } else {
                emit(
                        Resource.error(
                                data = null,
                                message = exception.message
                                        ?: context.getString(R.string.text_something_went_wrong)
                        )
                )
            }
        }
    }

    suspend fun addItemOnBookmark(resultEntity: ResultEntity) {
        val localDao = LocalDatabase.getDatabase(context).localDao()
        if (localDao.isRowIsExist(resultEntity.url)) {
            viewModelScope.launch {
                Toast.makeText(context, context.getString(R.string.already_bookmarked_message), Toast.LENGTH_SHORT).show()
            }
        } else {
            localDao.insert(resultEntity)
            viewModelScope.launch {
                Toast.makeText(context, context.getString(R.string.bookmarked_successfully), Toast.LENGTH_SHORT).show()
            }
        }
    }
}