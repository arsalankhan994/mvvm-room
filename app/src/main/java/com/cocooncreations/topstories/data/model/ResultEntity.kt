package com.cocooncreations.topstories.data.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "bookmark")
@Parcelize
class ResultEntity : Parcelable {
    @SerializedName("section")
    @Expose
    var section: String? = null

    @SerializedName("subsection")
    @Expose
    var subsection: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("abstract")
    @Expose
    var abstract: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

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
    var desFacet: List<String>? = null

    @SerializedName("org_facet")
    @Expose
    var orgFacet: List<String>? = null

    @SerializedName("per_facet")
    @Expose
    var perFacet: List<String>? = null

    @SerializedName("geo_facet")
    @Expose
    var geoFacet: List<String>? = null

    @SerializedName("multimedia")
    @Expose
    var multimedia: List<MultiMediumEntity>? = null

    @SerializedName("short_url")
    @Expose
    var shortUrl: String? = null
}