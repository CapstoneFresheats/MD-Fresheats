package com.capstone.fresheats.ui.profile

import android.view.View
import com.capstone.fresheats.model.response.Wrapper
import com.capstone.fresheats.model.response.logout.LogoutResponse
import com.capstone.fresheats.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePresenter(private var view: ProfileService.View) : ProfileService.Presenter {

    private var mCall: Call<Wrapper<Any>>? = null

    override fun submitLogout(viewParams: View) {
        view.showLoading()

        val call = ApiConfig.getInstance().getApi()?.logout()
        call?.enqueue(object : Callback<Wrapper<LogoutResponse>> {
            override fun onResponse(
                call: Call<Wrapper<LogoutResponse>>,
                response: Response<Wrapper<LogoutResponse>>
            ) {
                view.dismissLoading()
                if (response.isSuccessful) {
                    val wrapper = response.body()
                    if (wrapper?.meta?.status.equals("success", true)) {
                        val logoutResponse = wrapper?.data
                        if (logoutResponse != null) {
                            view.onLogoutSuccess(logoutResponse, viewParams)
                        } else {
                            view.onLogoutFailed("Logout response data is null")
                        }
                    } else {
                        view.onLogoutFailed(wrapper?.meta?.message ?: "Unknown error")
                    }
                } else {
                    view.onLogoutFailed("Response unsuccessful. Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Wrapper<LogoutResponse>>, t: Throwable) {
                view.dismissLoading()
                view.onLogoutFailed("Call failed: ${t.message}")
            }
        })
    }


//    override fun submitPhoto(profilePhotoPath: Uri) {
//        view.showLoading()
//
//        val token = Preferences.getApp().getToken()
//        val headers = HashMap<String, String>()
//        if (token != null) {
//            headers["Authorization"] = "Bearer $token"
//        }
//
//        val photoProfile = File(profilePhotoPath.path)
//        val profilePhotoReqBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), photoProfile)
//        val profilePhotoPart = MultipartBody.Part.createFormData("file", photoProfile.name, profilePhotoReqBody)
//
////        mCall = HttpClient.getInstance().getApiWithHeaders(headers)?.uploadPhoto(profilePhotoPart)
//
//        mCall?.enqueue(object : Callback<Wrapper<Any>> {
//            override fun onResponse(call: Call<Wrapper<Any>>, response: Response<Wrapper<Any>>) {
//                view.dismissLoading()
//                if (response.isSuccessful) {
//                    // Handle the response
//                } else {
//                    // Handle unsuccessful response
//                }
//            }
//
//            override fun onFailure(call: Call<Wrapper<Any>>, t: Throwable) {
//                view.dismissLoading()
//                // Handle the failure
//            }
//        })
//    }

    override fun subscribe() {
        // Implement if needed
    }

    override fun unsubscribe() {
        // Clear any subscriptions if needed
        mCall?.cancel()
    }
}