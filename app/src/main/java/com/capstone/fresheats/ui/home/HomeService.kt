package com.capstone.fresheats.ui.home

import com.capstone.fresheats.base.BasePresenter
import com.capstone.fresheats.base.BaseView
import com.capstone.fresheats.model.response.home.HomeResponse

interface HomeService {

    interface View : BaseView {
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message: String)
    }

    interface Presenter : BasePresenter {
        fun getHome()
    }
}
