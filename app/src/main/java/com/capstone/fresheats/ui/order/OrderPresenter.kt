package com.capstone.fresheats.ui.order

import com.capstone.fresheats.model.response.transaction.TransactionResponse
import com.capstone.fresheats.network.ApiConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class OrderPresenter(private val view: OrderService.View) : OrderService.Presenter {

    private var initialStatus: String? = null
    private val disposable = CompositeDisposable()

    override fun getTransaction() {
        view.showLoading()

        (view as? OrderFragment)?.clearDataAndViewPager()

        val observable = ApiConfig.getInstance().getApi()?.transaction()
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ response ->
                view.dismissLoading()
                if (response.meta?.status.equals("success", true)) {
                    response.data?.let { processData(it) }
                } else {
                    response.meta?.message?.let { view.onTransactionFailed(it) }
                }
            }, { t ->
                view.dismissLoading()
                view.onTransactionFailed(t.message.toString())
            })?.let {
                disposable.add(it)
            }
    }

    private fun processData(data: TransactionResponse) {
        val currentStatus = data.data.firstOrNull()?.status

        if (initialStatus == null) {
            initialStatus = currentStatus
        } else {
            // Check for status change from "on_delivery" to "cancelled"
            if (initialStatus.equals("on_delivery", true)
                && currentStatus.equals("cancelled", true)
            ) {
                moveDataToPastOrders(data)
            }
        }

        view.onTransactionSuccess(data)
    }

    private fun moveDataToPastOrders(data: TransactionResponse) {
        val fragment = view as? OrderFragment
        fragment?.inprogressList?.clear()
        fragment?.pastorderList?.addAll(data.data)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        disposable.clear()
    }
}
