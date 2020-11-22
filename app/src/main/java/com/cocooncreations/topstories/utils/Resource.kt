package com.cocooncreations.topstories.utils

data class Resource<out T>(val status: Status,val data: T?,val message: String?) {

    /*
    Here we are definig our generic methods for success, error and loading
    */
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String?): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}