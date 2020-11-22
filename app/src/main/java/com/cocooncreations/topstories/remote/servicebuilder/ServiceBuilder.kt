package com.cocooncreations.topstories.remote.servicebuilder

import com.cocooncreations.topstories.remote.constant.ApiConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {

    /*
    update interceptor for adding api-key on all calls as query parameter, so we can use this
    for all calls related to new york times
    */

    val apiKeyInterceptor = Interceptor { chain: Interceptor.Chain ->
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(ApiConstant.CONSTANT_API_KEY, ApiConstant.USER_API_KEY)
            .build()
        request = request.newBuilder().url(url).build()
        chain.proceed(request)
    }

    /*
    Create okhttp client and pass our interceptor
    */

    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    /*
    Finally pass okhttps client object to retrofit builder and also set baseURL
    */

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    /*
    for accessing Network calls
    */

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}