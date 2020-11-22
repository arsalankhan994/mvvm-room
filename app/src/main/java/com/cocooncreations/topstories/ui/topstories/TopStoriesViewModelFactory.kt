package com.cocooncreations.topstories.ui.topstories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cocooncreations.topstories.remote.services.NetworkService

class TopStoriesViewModelFactory(private val networkService: NetworkService, private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(TopStoriesViewModel::class.java)){
            return TopStoriesViewModel(networkService, context)as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}