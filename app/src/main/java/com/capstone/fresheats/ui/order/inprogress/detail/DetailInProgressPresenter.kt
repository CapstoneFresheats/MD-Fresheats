package com.capstone.fresheats.ui.order.inprogress.detail

import android.view.View
import com.capstone.fresheats.model.request.UpdateTransactionRequest
import com.capstone.fresheats.model.response.Wrapper
import com.capstone.fresheats.model.response.transaction.updateTransaction.TransactionUpdateResponse
import com.capstone.fresheats.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailInProgressPresenter(
    private val view: UpdateTransactionService.View
) : UpdateTransactionService.Presenter {

    private var mCall: Call<Wrapper<TransactionUpdateResponse>>? = null
    private var transactionId: Int? = null

    override fun performTransactionUpdate(id: Int, updateTransactionRequest: UpdateTransactionRequest, viewParams: View?) {
        transactionId = id
        view.showLoading()

        mCall?.cancel()

        mCall = ApiConfig.getInstance().getApi()!!.transactionUpdate(
            id,
            updateTransactionRequest.status.toString()
        )

        mCall?.enqueue(object : Callback<Wrapper<TransactionUpdateResponse>> {
            override fun onResponse(
                call: Call<Wrapper<TransactionUpdateResponse>>,
                response: Response<Wrapper<TransactionUpdateResponse>>
            ) {
                if (response.isSuccessful) {
                    val dataTransactions = response.body()?.data
                    if (dataTransactions != null) {
                        view.showTransactionUpdateSuccess(dataTransactions)
                    } else {
                        view.showTransactionUpdateError("Failed to get transaction data")
                    }
                } else {
                    view.showTransactionUpdateError("Failed to update transaction")
                }

                view.dismissLoading()
            }

            override fun onFailure(call: Call<Wrapper<TransactionUpdateResponse>>, t: Throwable) {
                view.showTransactionUpdateError("Network error: ${t.message}")
                view.dismissLoading()
            }
        })
    }

    override fun onDestroy() {
        mCall?.cancel()
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCall?.cancel()
    }
}