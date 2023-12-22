package com.capstone.fresheats.network

import com.capstone.fresheats.model.response.Wrapper
import com.capstone.fresheats.model.response.checkout.CheckoutResponse
import com.capstone.fresheats.model.response.home.HomeResponse
import com.capstone.fresheats.model.response.login.LoginResponse
import com.capstone.fresheats.model.response.logout.LogoutResponse
import com.capstone.fresheats.model.response.transaction.TransactionResponse
import com.capstone.fresheats.model.response.transaction.updateTransaction.TransactionUpdateResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<Wrapper<LoginResponse>>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
        @Field("address") address: String,
        @Field("phone_number") phone_number: String,
        @Field("city") city: String,
    ) : Call<Wrapper<LoginResponse>>

    @GET("ingredients")
    @Headers("Accept: application/json")
    fun ingredients(): Call<Wrapper<HomeResponse>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("checkout")
    fun checkout(
        @Field("user_id") userId: String,
        @Field("ingredients_id") ingredientsId: String,
        @Field("quantity") quantity: String,
        @Field("total") total: String,
        @Field("status") status: String,
    ) : Call<Wrapper<CheckoutResponse>>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("transaction/{id}")
    fun transactionUpdate(
        @Path("id") id: Int,
        @Field("status") status: String
    ): Call<Wrapper<TransactionUpdateResponse>>

    @Headers("Accept: application/json")
    @GET("transaction")
    fun transaction() : Observable<Wrapper<TransactionResponse>>

    @Headers("Accept: application/json")
    @POST("logout")
    fun logout() : Call<Wrapper<LogoutResponse>>
}