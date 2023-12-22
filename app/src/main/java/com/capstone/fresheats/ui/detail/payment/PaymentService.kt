package com.capstone.fresheats.ui.detail.payment

import com.capstone.fresheats.base.BasePresenter
import com.capstone.fresheats.base.BaseView
import com.capstone.fresheats.model.request.CheckoutRequest
import com.capstone.fresheats.model.response.checkout.CheckoutResponse

interface PaymentService {

    interface View : BaseView {
        fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: android.view.View)
        fun onCheckoutFailed(message: String)
    }

    interface Presenter : PaymentService, BasePresenter {
        fun getCheckout(checkoutRequest: CheckoutRequest, view: android.view.View)
    }
}
