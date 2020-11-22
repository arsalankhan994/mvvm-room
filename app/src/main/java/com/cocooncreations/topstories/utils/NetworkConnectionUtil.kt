package com.cocooncreations.topstories.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class NetworkConnectionUtil {
    companion object {
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
    }
}