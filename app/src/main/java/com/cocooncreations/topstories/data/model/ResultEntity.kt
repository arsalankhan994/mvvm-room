package com.cocooncreations.topstories.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cocooncreations.topstories.utils.CustomTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bookmark")
open class ResultEntity() : Parcelable {
    @SerializedName("section")
    @Expose
    var section: String? = null

    @SerializedName("subsection")
    @Expose
    var subsection: String? = null

    @PrimaryKey
    @SerializedName("url")
    @Expose
    var url: String = ""

    @SerializedName("uri")
    @Expose
    var uri: String? = null

    @SerializedName("byline")
    @Expose
    var byline: String? = null

    @SerializedName("item_type")
    @Expose
    var itemType: String? = null

    @SerializedName("updated_date")
    @Expose
    var updatedDate: String? = null

    @SerializedName("created_date")
    @Expose
    var createdDate: String? = null

    @SerializedName("published_date")
    @Expose
    var publishedDate: String? = null

    @SerializedName("material_type_facet")
    @Expose
    var materialTypeFacet: String? = null

    @SerializedName("kicker")
    @Expose
    var kicker: String? = null

    @SerializedName("des_facet")
    @Expose
    @TypeConverters(CustomTypeConverter::class)
    var desFacet: List<String>? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("abstract")
    @Expose
    var abstractString: String? = null

    @SerializedName("org_facet")
    @Expose
    @TypeConverters(CustomTypeConverter::class)
    var orgFacet: List<String>? = null

    @SerializedName("per_facet")
    @Expose
    @TypeConverters(CustomTypeConverter::class)
    var perFacet: List<String>? = null

    @SerializedName("geo_facet")
    @Expose
    @TypeConverters(CustomTypeConverter::class)
    var geoFacet: List<String>? = null

    @SerializedName("multimedia")
    @Expose
    @TypeConverters(CustomTypeConverter::class)
    var multimedia: List<MultiMediumEntity>? = null

    @SerializedName("short_url")
    @Expose
    var shortUrl: String? = null

    constructor(parcel: Parcel) : this() {
        section = parcel.readString()
        subsection = parcel.readString()
        title = parcel.readString()
        abstractString = parcel.readString()
        url = parcel.readString()!!
        uri = parcel.readString()
        byline = parcel.readString()
        itemType = parcel.readString()
        updatedDate = parcel.readString()
        createdDate = parcel.readString()
        publishedDate = parcel.readString()
        materialTypeFacet = parcel.readString()
        kicker = parcel.readString()
        desFacet = parcel.createStringArrayList()
        orgFacet = parcel.createStringArrayList()
        perFacet = parcel.createStringArrayList()
        geoFacet = parcel.createStringArrayList()
        multimedia = parcel.createTypedArrayList(MultiMediumEntity)
        shortUrl = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(section)
        parcel.writeString(subsection)
        parcel.writeString(title)
        parcel.writeString(abstractString)
        parcel.writeString(url)
        parcel.writeString(uri)
        parcel.writeString(byline)
        parcel.writeString(itemType)
        parcel.writeString(updatedDate)
        parcel.writeString(createdDate)
        parcel.writeString(publishedDate)
        parcel.writeString(materialTypeFacet)
        parcel.writeString(kicker)
        parcel.writeStringList(desFacet)
        parcel.writeStringList(orgFacet)
        parcel.writeStringList(perFacet)
        parcel.writeStringList(geoFacet)
        parcel.writeTypedList(multimedia)
        parcel.writeString(shortUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultEntity> {
        override fun createFromParcel(parcel: Parcel): ResultEntity {
            return ResultEntity(parcel)
        }

        override fun newArray(size: Int): Array<ResultEntity?> {
            return arrayOfNulls(size)
        }
    }
}