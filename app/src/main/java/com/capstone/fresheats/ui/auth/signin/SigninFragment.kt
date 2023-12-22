package com.capstone.fresheats.ui.auth.signin

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.fresheats.databinding.FragmentSigninBinding
import com.capstone.fresheats.model.response.login.LoginResponse
import com.capstone.fresheats.ui.auth.AuthActivity
import com.capstone.fresheats.Preferences
import com.capstone.fresheats.MainActivity
import com.capstone.fresheats.R
import com.google.gson.Gson

class SigninFragment : Fragment(), SigninService.View {

    private lateinit var presenter: SigninPresenter
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!

    private var loading: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SigninPresenter(this)

        if (!Preferences.getApp().getToken().isNullOrEmpty()){
            launchMainActivity()
        }

//        initDummy()
        initViewLoading()

        binding.btnSignup.setOnClickListener {
            val signup = Intent(activity, AuthActivity::class.java)
            signup.putExtra("page_request", 2)
            startActivity(signup)
        }

        playAnimation()

        binding.btnSignin.setOnClickListener {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty()){
                binding.etEmail.error = "Silahkan masukkan Email Anda"
                binding.etEmail.requestFocus()
            } else if (password.isEmpty()){
                binding.etPassword.error = "Silahkan masukkan Password Anda"
                binding.etPassword.requestFocus()
            } else {
                presenter.submitLogin(email, password)
            }
        }
    }

    private fun initViewLoading() {
        loading = Dialog(binding.root.context)
        val progressBarLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        loading?.let {
            it.setContentView(progressBarLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun showLoading() {
        if (!isAdded || !isVisible || requireActivity().isFinishing || isStateSaved) {
            return
        }
        loading?.show()
    }

    override fun dismissLoading() {
        if (!isAdded || !isVisible || requireActivity().isFinishing) {
            return
        }
        loading?.dismiss()
    }

//    private fun initDummy(){
//        binding.etEmail.setText("testtt123@gmail.com")
//        binding.etPassword.setText("12345678")
//    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {
        val token = loginResponse.access_token

        // Save token and user data to Preferences
        Preferences.getApp().setToken(token)

        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        Preferences.getApp().setUser(json)

        launchMainActivity()
    }

    override fun onLoginFailed(message: String) {
        context?.let {
            Toast.makeText(it, "Email dan Password yang anda masukkan salah", Toast.LENGTH_SHORT).show()
        } ?: run {
            Log.e("SigninFragment", "Context is null when trying to show Toast. Message: $message")
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivImage, View.TRANSLATION_X, -40f, 40f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val textemail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(250)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.etEmail, View.ALPHA, 1f).setDuration(250)
        val textpassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(250)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordInputLayout, View.ALPHA, 1f).setDuration(250)
        val signIn = ObjectAnimator.ofFloat(binding.btnSignin, View.ALPHA, 1f).setDuration(250)
        val haveAccount = ObjectAnimator.ofFloat(binding.HaveAccount, View.ALPHA, 1f).setDuration(250)
        val signUp = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(250)

        AnimatorSet().apply {
            playSequentially(
                textemail,
                emailEditTextLayout,
                textpassword,
                passwordTextView,
                signIn,
                haveAccount,
                signUp,
            )
            startDelay = 500
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        loading?.dismiss()
    }

    private fun launchMainActivity() {
        val home = Intent(activity, MainActivity::class.java)
        startActivity(home)
        activity?.finish()
    }
}