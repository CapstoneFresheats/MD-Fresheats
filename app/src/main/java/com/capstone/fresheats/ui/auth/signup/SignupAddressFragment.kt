package com.capstone.fresheats.ui.auth.signup

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.fresheats.Preferences
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentSignupAddressBinding
import com.capstone.fresheats.model.request.RegisterRequest
import com.capstone.fresheats.model.response.login.LoginResponse
import com.capstone.fresheats.ui.auth.AuthActivity
import com.google.gson.Gson

class SignupAddressFragment : Fragment(), SignupService.View, View.OnClickListener {

    private var _binding: FragmentSignupAddressBinding? = null
    private val binding get() = _binding!!

    private lateinit var data: RegisterRequest
    private lateinit var presenter: SignupPresenter
    private var loading: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = SignupPresenter(this)
        data = arguments?.getParcelable("data")!!

        binding.btnSignUpNow.setOnClickListener(this)

        initViewLoading()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnSignUpNow -> {
                actionRegisterAddress(view)
            }
        }
    }

    private fun actionRegisterAddress(view: View?) {
        val phone = binding.etPhoneNumber.text.toString()
        val address = binding.etAddress.text.toString()
        val city = binding.etCity.text.toString()

        if (phone.isEmpty()) {
            showError(binding.etPhoneNumber, "Silahkan masukkan nomor phone")
        } else if (address.isEmpty()) {
            showError(binding.etAddress, "Silahkan masukkan nomor address")
        } else if (city.isEmpty()) {
            showError(binding.etCity, "Silahkan masukkan house number city")
        } else {
            data.apply {
                this.address = address
                this.city = city
                this.phone_number = phone
            }

            Log.d("DATA_USER", data.toString())
            view?.let { presenter.submitRegister(data, it) }
        }
    }

//    private fun initDummy() {
//        binding.etPhoneNumber.setText("0987654321")
//        binding.etAddress.setText("Jalan Jendelan Gajah")
//        binding.etCity.setText("Depok")
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRegisterSuccess(loginResponse: LoginResponse, view: View) {

        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        Preferences.getApp().setUser(json)

        val home = Intent(activity, AuthActivity::class.java)
        startActivity(home)
        activity?.finish()
        Toast.makeText(activity, "Pendaftaran Berhasil, Silahkan Login", Toast.LENGTH_LONG).show()
    }

    override fun onRegisterFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
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
        loading?.show()
    }

    override fun dismissLoading() {
        loading?.dismiss()
    }

    private fun showError(view: View?, message: String) {
        if (view is androidx.appcompat.widget.AppCompatEditText) {
            view.error = message
            view.requestFocus()
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        }
    }
}