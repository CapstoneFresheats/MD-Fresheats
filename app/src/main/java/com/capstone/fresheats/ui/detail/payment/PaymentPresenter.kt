package com.capstone.fresheats.ui.detail.payment

import android.view.View
import com.capstone.fresheats.model.request.CheckoutRequest
import com.capstone.fresheats.model.response.Wrapper
import com.capstone.fresheats.model.response.checkout.CheckoutResponse
import com.capstone.fresheats.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentPresenter(private val view: PaymentService.View) : PaymentService.Presenter {

    private var mCall: Call<Wrapper<CheckoutResponse>>? = null

    override fun getCheckout(checkoutRequest: CheckoutRequest, view: View) {
        this.view.showLoading()

        mCall?.cancel()

        mCall = ApiConfig.getInstance().getApi()!!.checkout(
            checkoutRequest.foodId.toString(),
            checkoutRequest.userId.toString(),
            checkoutRequest.quantity.toString(),
            checkoutRequest.total.toString(),
            checkoutRequest.status.toString()
        )

        mCall?.enqueue(object : Callback<Wrapper<CheckoutResponse>> {
            override fun onResponse(call: Call<Wrapper<CheckoutResponse>>, response: Response<Wrapper<CheckoutResponse>>) {
                this@PaymentPresenter.view.dismissLoading()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.meta?.status.equals("success", true)) {
                        responseBody?.data?.let { this@PaymentPresenter.view.onCheckoutSuccess(it, view) }
                    } else {
                        responseBody?.meta?.message?.let { this@PaymentPresenter.view.onCheckoutFailed(it) }
                    }
                } else {
                    this@PaymentPresenter.view.onCheckoutFailed("Request failed with code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Wrapper<CheckoutResponse>>, t: Throwable) {
                this@PaymentPresenter.view.dismissLoading()
                this@PaymentPresenter.view.onCheckoutFailed(t.message.toString())
            }
        })
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCall?.cancel()
    }
}