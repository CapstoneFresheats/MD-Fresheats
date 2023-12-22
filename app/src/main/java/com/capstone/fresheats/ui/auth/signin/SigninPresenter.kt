package com.capstone.fresheats.ui.auth.signin

import com.capstone.fresheats.Preferences
import com.capstone.fresheats.network.ApiConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SigninPresenter(private var view: SigninService.View) : SigninService.Presenter {

    private var isAttached: Boolean = false
    private val disposable = CompositeDisposable()

    init {
        isAttached = true
    }

    override fun submitLogin(email: String, password: String) {
        view.showLoading()

        val observable = ApiConfig.getInstance().getApi()?.login(email, password)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ response ->
                view.dismissLoading()

                if (isAttached) {
                    if (response.meta?.status.equals("success", true)) {
                        response.data?.let { data ->
                            Preferences.getApp().setToken(data.access_token)
                            view.onLoginSuccess(data)
                        }
                    } else {
                        response.meta?.message?.let { message -> view.onLoginFailed(message) }
                    }
                }
            }, { t ->
                view.dismissLoading()

                if (isAttached) {
                    view.onLoginFailed(t.message.toString())
                }
            })?.let {
                disposable.add(it)
            }
    }

    override fun isViewAttached(): Boolean {
        return isAttached
    }

    override fun subscribe() {}

    override fun unsubscribe() {
        disposable.clear()
    }
}