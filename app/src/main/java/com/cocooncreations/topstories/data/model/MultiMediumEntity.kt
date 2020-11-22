package com.cocooncreations.topstories.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

open class MultiMediumEntity() : Parcelable {
    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("format")
    @Expose
    var format: String? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null

    @SerializedName("width")
    @Expose
    var width: Int? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("subtype")
    @Expose
    var subtype: String? = null

    @SerializedName("caption")
    @Expose
    var caption: String? = null

    @SerializedName("copyright")
    @Expose
    var copyright: String? = null

    constructor(parcel: Parcel) : this() {
        url = parcel.readString()
        format = parcel.readString()
        height = parcel.readValue(Int::class.java.classLoader) as? Int
        width = parcel.readValue(Int::class.java.classLoader) as? Int
        type = parcel.readString()
        subtype = parcel.readString()
        caption = parcel.readString()
        copyright = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(format)
        parcel.writeValue(height)
        parcel.writeValue(width)
        parcel.writeString(type)
        parcel.writeString(subtype)
        parcel.writeString(caption)
        parcel.writeString(copyright)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MultiMediumEntity> {
        override fun createFromParcel(parcel: Parcel): MultiMediumEntity {
            return MultiMediumEntity(parcel)
        }

        override fun newArray(size: Int): Array<MultiMediumEntity?> {
            return arrayOfNulls(size)
        }
    }

}