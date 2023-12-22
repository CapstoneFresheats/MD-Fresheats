package com.capstone.fresheats.model.response.logout


import com.capstone.fresheats.model.response.Meta
import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("data")
    val data1: Data1,
    @SerializedName("meta")
    val meta: Meta
)