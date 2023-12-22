package com.capstone.fresheats.ui.detail.payment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.fresheats.MainActivity
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentPaymentSuccessBinding
import com.capstone.fresheats.ui.detail.DetailActivity

class PaymentSuccessFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPaymentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPaymentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as DetailActivity).toolbarDetail()

        binding.btnOrderOther.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnOrderOther -> {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finishAffinity()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}