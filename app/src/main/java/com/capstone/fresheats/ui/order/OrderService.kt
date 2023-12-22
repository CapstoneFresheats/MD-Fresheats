package com.capstone.fresheats.ui.order

import com.capstone.fresheats.base.BasePresenter
import com.capstone.fresheats.base.BaseView
import com.capstone.fresheats.model.response.transaction.TransactionResponse

interface OrderService {

    interface View : BaseView {
        fun onTransactionSuccess(transactionResponse: TransactionResponse)
        fun onTransactionFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun getTransaction()
    }
}
