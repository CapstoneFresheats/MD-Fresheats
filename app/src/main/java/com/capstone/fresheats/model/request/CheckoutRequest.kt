package com.capstone.fresheats.model.request

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckoutRequest (
    @Expose
    @SerializedName("ingredients_id")
    var foodId : String? = null,
    @Expose
    @SerializedName("user_id")
    var userId : String? = null,
    @Expose
    @SerializedName("quantity")
    var quantity : String? = null,
    @Expose
    @SerializedName("total")
    var total : String? = null,
    @Expose
    @SerializedName("status")
    var status : String? = null,
    ) : Parcelable