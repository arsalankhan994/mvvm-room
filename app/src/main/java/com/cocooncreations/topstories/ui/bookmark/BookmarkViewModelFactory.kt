package com.cocooncreations.topstories.ui.bookmark

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BookmarkViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    /*
    Here we are using factory method for our viewmodel object
    */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BookmarkViewModel::class.java)){
            return BookmarkViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}