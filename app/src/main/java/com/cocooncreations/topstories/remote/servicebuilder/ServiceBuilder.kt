package com.cocooncreations.topstories.remote.servicebuilder

import com.cocooncreations.topstories.remote.constant.ApiConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {

    val apiKeyInterceptor = Interceptor { chain: Interceptor.Chain ->
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter(ApiConstant.CONSTANT_API_KEY, ApiConstant.USER_API_KEY)
            .build()
        request = request.newBuilder().url(url).build()
        chain.proceed(request)
    }

    var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}