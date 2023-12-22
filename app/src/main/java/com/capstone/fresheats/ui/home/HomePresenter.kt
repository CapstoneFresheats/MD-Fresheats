package com.capstone.fresheats.ui.home

import com.capstone.fresheats.model.response.Wrapper
import com.capstone.fresheats.model.response.home.HomeResponse
import com.capstone.fresheats.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(private val view: HomeService.View) : HomeService.Presenter {

    override fun getHome() {
        view.showLoading()

        val call = ApiConfig.getInstance().getApi()!!.ingredients()

        call.enqueue(object : Callback<Wrapper<HomeResponse>> {
            override fun onResponse(
                call: Call<Wrapper<HomeResponse>>,
                response: Response<Wrapper<HomeResponse>>
            ) {
                view.dismissLoading()
                if (response.isSuccessful) {
                    val it = response.body()
                    if (it?.meta?.status.equals("success", true)) {
                        it?.data?.let { it1 -> view.onHomeSuccess(it1) }
                    } else {
                        it?.meta?.message?.let { it1 -> view.onHomeFailed(it1) }
                    }
                } else {
                    view.onHomeFailed("Response unsuccessful. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Wrapper<HomeResponse>>, t: Throwable) {
                view.dismissLoading()
                view.onHomeFailed("Call failed: ${t.message}")
            }
        })
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }
}