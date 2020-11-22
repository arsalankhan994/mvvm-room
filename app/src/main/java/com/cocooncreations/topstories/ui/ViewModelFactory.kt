package com.cocooncreations.topstories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cocooncreations.topstories.remote.services.NetworkService
import com.cocooncreations.topstories.ui.home.TopStoriesViewModel

class ViewModelFactory(private val networkService: NetworkService) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(TopStoriesViewModel::class.java)){
            return TopStoriesViewModel(networkService)as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}