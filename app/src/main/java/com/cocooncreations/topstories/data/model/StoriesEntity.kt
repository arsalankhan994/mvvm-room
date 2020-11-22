package com.cocooncreations.topstories.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


open class StoriesEntity() : Parcelable {
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

    constructor(parcel: Parcel) : this() {
        status = parcel.readString()
        copyright = parcel.readString()
        section = parcel.readString()
        lastUpdated = parcel.readString()
        numResults = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeString(copyright)
        parcel.writeString(section)
        parcel.writeString(lastUpdated)
        parcel.writeValue(numResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoriesEntity> {
        override fun createFromParcel(parcel: Parcel): StoriesEntity {
            return StoriesEntity(parcel)
        }

        override fun newArray(size: Int): Array<StoriesEntity?> {
            return arrayOfNulls(size)
        }
    }
}