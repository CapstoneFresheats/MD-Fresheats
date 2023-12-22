package com.capstone.fresheats.ui.auth.signup

import android.util.Log
import android.view.View
import com.capstone.fresheats.model.request.RegisterRequest
import com.capstone.fresheats.model.response.Wrapper
import com.capstone.fresheats.model.response.login.LoginResponse
import com.capstone.fresheats.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupPresenter(private val view: SignupService.View) : SignupService.Presenter {

    private val mCallList: MutableList<Call<*>> = ArrayList()

    override fun submitRegister(registerRequest: RegisterRequest, view: View) {
        this.view.showLoading()


        val call = ApiConfig.getInstance().getApi()!!.register(
            registerRequest.name,
            registerRequest.email,
            registerRequest.password,
            registerRequest.passwordConfirmation,
            registerRequest.address,
            registerRequest.phone_number,
            registerRequest.city
        )

        mCallList.add(call)

        call.enqueue(object : Callback<Wrapper<LoginResponse>> {
            override fun onResponse(
                call: Call<Wrapper<LoginResponse>>,
                response: Response<Wrapper<LoginResponse>>
            ) {
                this@SignupPresenter.view.dismissLoading()

                if (response.isSuccessful) {
                    // Proses respons yang berhasil
                    val it = response.body()
                    if (it?.meta?.status.equals("success", true)) {
                        it?.data?.let { it1 -> this@SignupPresenter.view.onRegisterSuccess(it1, view) }
                    } else {
                        it?.meta?.message?.let { it1 -> this@SignupPresenter.view.onRegisterFailed(it1) }
                    }
                } else {
                    // Proses respons kesalahan
                    this@SignupPresenter.view.onRegisterFailed(response.message())
                    Log.e("Registration", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Wrapper<LoginResponse>>, t: Throwable) {
                // Tangani kegagalan
                this@SignupPresenter.view.dismissLoading()
                this@SignupPresenter.view.onRegisterFailed(t.message.toString())
                Log.e("Registration", "Failure: ${t.message}")
            }
        })
    }

    override fun subscribe() {}

    override fun unsubscribe() {
        for (call in mCallList) {
            call.cancel()
        }
    }
}