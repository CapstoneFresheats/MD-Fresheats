package com.capstone.fresheats.ui.order.inprogress.detail

import android.view.View
import com.capstone.fresheats.base.BasePresenter
import com.capstone.fresheats.base.BaseView
import com.capstone.fresheats.model.request.UpdateTransactionRequest
import com.capstone.fresheats.model.response.transaction.updateTransaction.TransactionUpdateResponse

interface UpdateTransactionService {
    interface View : BaseView {
        fun showTransactionUpdateSuccess(dataTransaction2: TransactionUpdateResponse?)
        fun showTransactionUpdateError(message: String)
    }

    interface Presenter : BasePresenter {
        fun performTransactionUpdate(id: Int, updateTransactionRequest: UpdateTransactionRequest, viewParams: android.view.View?)
        fun onDestroy()
    }
}
