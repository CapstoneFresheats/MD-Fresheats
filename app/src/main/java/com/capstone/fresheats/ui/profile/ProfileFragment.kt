package com.capstone.fresheats.ui.profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.capstone.fresheats.Preferences
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentProfileBinding
import com.capstone.fresheats.model.response.login.User
import com.capstone.fresheats.model.response.logout.LogoutResponse
import com.capstone.fresheats.ui.auth.AuthActivity
import com.capstone.fresheats.ui.profile.generalsetting.ConditionActivity
import com.capstone.fresheats.ui.profile.generalsetting.PrivacyPolicyActivity
import com.google.gson.Gson

class ProfileFragment : Fragment(), ProfileService.View, View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var loading: Dialog? = null
    private lateinit var presenterProfile: ProfilePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = Preferences.getApp().getUser()
        val userResponse = Gson().fromJson(user, User::class.java)

        binding.tvName.text = userResponse.name
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenterProfile = ProfilePresenter(this)

        binding.btnaccount.setOnClickListener(this)
        binding.btncondition.setOnClickListener(this)
        binding.btnprivacypolicy.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnaccount -> {
                Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_LONG).show()
            }
            R.id.btncondition -> {
                val intent = Intent(activity, ConditionActivity::class.java)
                startActivity(intent)
            }
            R.id.btnprivacypolicy -> {
                val intent = Intent(activity, PrivacyPolicyActivity::class.java)
                startActivity(intent)
            }
            R.id.btnLogout -> {
                actionLogout(view)
            }
        }
    }

    private fun actionLogout(view: View) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Keluar Dari Akun Anda?")

        builder.setPositiveButton("Iya") { dialog, which ->
            presenterProfile.submitLogout(view)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onLogoutSuccess(logoutResponse: LogoutResponse, view: View) {
        Preferences.getApp().setUser("")
        Preferences.getApp().setToken("")

        if (Preferences.getApp().getToken().isNullOrEmpty()) {
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun onLogoutFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        if (isAdded && isVisible && !requireActivity().isFinishing && !isStateSaved) {
            loading?.show()
        }
    }

    override fun dismissLoading() {
        if (isAdded && isVisible && !requireActivity().isFinishing) {
            loading?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}