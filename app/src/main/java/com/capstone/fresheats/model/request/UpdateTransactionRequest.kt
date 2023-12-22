package com.capstone.fresheats.model.request

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class UpdateTransactionRequest (
    @Expose
    @SerializedName("status")
    var status : String? = null,
) : Parcelable