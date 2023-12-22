package com.capstone.fresheats.ui.auth.signin

import com.capstone.fresheats.base.BasePresenter
import com.capstone.fresheats.base.BaseView
import com.capstone.fresheats.model.response.login.LoginResponse

interface SigninService {

    interface View : BaseView {
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun submitLogin(email: String, password: String)
        fun isViewAttached(): Boolean
    }
}
