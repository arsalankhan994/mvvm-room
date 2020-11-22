package com.cocooncreations.topstories.remote.services

import com.cocooncreations.topstories.data.model.StoriesEntity
import com.cocooncreations.topstories.remote.constant.ApiConstant
import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {
    @GET(ApiConstant.TOP_STORIES)
    suspend fun getTopStories(): StoriesEntity
}