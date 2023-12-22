package com.capstone.fresheats.model.response.transaction.updateTransaction

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTransaction2(
    @Expose
    @SerializedName("created_at")
    val createdAt: String,
    @Expose
    @SerializedName("deleted_at")
    val deletedAt: String?,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("ingredients_id")
    val ingredientsId: Int,
    @Expose
    @SerializedName("payment_url")
    val paymentUrl: String,
    @Expose
    @SerializedName("quantity")
    val quantity: Int,
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("total")
    val total: String,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: String,
    @Expose
    @SerializedName("user_id")
    val userId: Int
): Parcelable