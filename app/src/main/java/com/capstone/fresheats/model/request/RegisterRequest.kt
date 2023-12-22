package com.capstone.fresheats.model.request

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RegisterRequest(
    @Expose
    @SerializedName("name")
    var name : String,
    @Expose
    @SerializedName("email")
    var email : String,
    @Expose
    @SerializedName("password")
    var password : String,
    @Expose
    @SerializedName("password_confirmation")
    var passwordConfirmation : String,
    @Expose
    @SerializedName("address")
    var address : String,
    @Expose
    @SerializedName("phone_number")
    var phone_number : String,
    @Expose
    @SerializedName("city")
    var city : String
) : Parcelable
