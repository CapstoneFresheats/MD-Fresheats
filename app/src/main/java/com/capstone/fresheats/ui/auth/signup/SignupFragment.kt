package com.capstone.fresheats.ui.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentSignupBinding
import com.capstone.fresheats.model.request.RegisterRequest
import com.capstone.fresheats.ui.auth.AuthActivity

class SignupFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener(this)
//        initDummy()
    }


//    private fun initDummy() {
//        binding.etFullname.setText("jennie BlackPink")
//        binding.etEmail.setText("jennieBlackPink@gmail.com")
//        binding.etPassword.setText("12345678")
//        binding.etConfirmPassword.setText("12345678")
//    }

    private fun actionRegister(view : View?){
        view?.let {

            val fullname = binding.etFullname.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()

            if (fullname.isEmpty()) {
                binding.etFullname.error = "Silahkan masukkan Nama Lengkap"
                binding.etFullname.requestFocus()
            } else if (email.isEmpty()) {
                binding.etEmail.error = "Silahkan masukkan email"
                binding.etEmail.requestFocus()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Format email tidak valid"
                binding.etEmail.requestFocus()
            } else if (password.isEmpty()) {
                binding.etPassword.error = "Silahkan masukkan password"
                binding.etPassword.requestFocus()
            } else if (password.length < 8) {
                binding.etPassword.error = "Password harus memiliki setidaknya 8 karakter"
                binding.etPassword.requestFocus()
            } else if (confirmPassword.isEmpty()) {
                binding.etConfirmPassword.error = "Silahkan konfirmasi password"
                binding.etConfirmPassword.requestFocus()
            } else if (password != confirmPassword) {
                binding.etConfirmPassword.error = "Konfirmasi Password harus sama dengan password"
                binding.etConfirmPassword.requestFocus()
            } else {
                val data = RegisterRequest(
                    fullname,
                    email,
                    password,
                    confirmPassword,
                    "", "", ""
                )

                val bundle = Bundle()
                bundle.putParcelable("data", data)
                Navigation.findNavController(it)
                    .navigate(R.id.fragmentSignUpAddress,bundle)
                (activity as AuthActivity).tolbarSignUp()
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnContinue -> {
                actionRegister(view)
            }
        }
    }
}