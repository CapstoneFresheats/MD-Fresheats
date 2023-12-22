package com.capstone.fresheats.model.response.checkout

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckoutResponse(
    @Expose
    @SerializedName("created_at")
    val createdAt: String,
    @Expose
    @SerializedName("deleted_at")
    val deletedAt: Any,
    @Expose
    @SerializedName("ingredients")
    val ingredients: Ingredients,
    @Expose
    @SerializedName("ingredients_id")
    val foodId: Int,
    @Expose
    @SerializedName("id")
    val id: Int,
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
    val total: Int,
    @Expose
    @SerializedName("updated_at")
    val updatedAt: String,
    @Expose
    @SerializedName("user")
    val user: User1,
    @Expose
    @SerializedName("user_id")
    val userId: Int
)