package com.capstone.fresheats.model.response.transaction.updateTransaction


import com.capstone.fresheats.model.response.Meta
import com.google.gson.annotations.SerializedName

data class TransactionUpdateResponse(
    @SerializedName("data")
    val `data`: DataTransaction2,
    @SerializedName("meta")
    val meta: Meta
)