package com.cocooncreations.topstories.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cocooncreations.topstories.remote.services.NetworkService
import com.cocooncreations.topstories.utils.Resource
import kotlinx.coroutines.Dispatchers

class TopStoriesViewModel(private val networkService: NetworkService) : ViewModel() {

    fun getTopStories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data=networkService.getTopStories()))
        }catch (exception: Exception){
            emit(Resource.error(data=null,message = exception.message?:"Error Occured"))
        }
    }
}