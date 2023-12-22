package com.capstone.fresheats.model.response.transaction

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredients(
    @Expose
    @SerializedName("created_at")
    val createdAt: Int,
    @Expose
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("picture_path")
    val picturePath: String,
    @Expose
    @SerializedName("price")
    val price: Int,
    @Expose
    @SerializedName("rate")
    val rate: Double,
    @Expose
    @SerializedName("stock")
    val stock: Int?,
    @Expose
    @SerializedName("types")
    val types: String,
    @Expose
    @SerializedName("picture_path_url")
    val picturePathUrl: String?,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: Int?
): Parcelable