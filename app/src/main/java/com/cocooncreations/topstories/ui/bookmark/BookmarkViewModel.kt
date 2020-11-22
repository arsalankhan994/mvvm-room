package com.cocooncreations.topstories.ui.bookmark

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cocooncreations.topstories.R
import com.cocooncreations.topstories.data.local.LocalDatabase
import com.cocooncreations.topstories.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.net.UnknownHostException

class BookmarkViewModel(private var context: Context) : ViewModel() {

    /*
    Getting bookmark list from our database
    and incase of exception, we are showing error message
    */
    fun getBookmarkList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val localDao = LocalDatabase.getDatabase(context).localDao()
            emit(Resource.success(data = localDao.getAllBookMark()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message
                ?: context.getString(R.string.text_something_went_wrong)))
        }
    }
}