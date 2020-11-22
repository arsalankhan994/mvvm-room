package com.cocooncreations.topstories.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class StoriesEntity : Parcelable {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("copyright")
    @Expose
    var copyright: String? = null

    @SerializedName("section")
    @Expose
    var section: String? = null

    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String? = null

    @SerializedName("num_results")
    @Expose
    var numResults: Int? = null

    @SerializedName("results")
    @Expose
    var resultEntities: MutableList<ResultEntity>? = null
}