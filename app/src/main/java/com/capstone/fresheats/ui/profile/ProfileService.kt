package com.capstone.fresheats.ui.profile

import com.capstone.fresheats.base.BasePresenter
import com.capstone.fresheats.base.BaseView
import com.capstone.fresheats.model.response.logout.LogoutResponse

interface ProfileService {

    interface View : BaseView {
//        fun onUpdatePhotoSuccess(updateProfileResponse: UpdateProfileResponse)
//        fun onUpdatePhotoFailed(message : String)
        fun onLogoutSuccess(logoutResponse: LogoutResponse, view : android.view.View)
        fun onLogoutFailed(mesage : String)
    }

    interface Presenter: ProfileService, BasePresenter {
        fun submitLogout(view : android.view.View)
//        fun submitPhoto(profilePhotoPath: Uri)
    }

}
