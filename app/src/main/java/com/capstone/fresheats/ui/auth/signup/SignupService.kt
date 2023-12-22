package com.capstone.fresheats.ui.auth.signup

import com.capstone.fresheats.base.BasePresenter
import com.capstone.fresheats.base.BaseView
import com.capstone.fresheats.model.request.RegisterRequest
import com.capstone.fresheats.model.response.login.LoginResponse

interface SignupService {

    interface View : BaseView {
        fun onRegisterSuccess(loginResponse: LoginResponse, view:android.view.View)
        fun onRegisterFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun submitRegister(registerRequest: RegisterRequest, view:android.view.View)
    }
}
