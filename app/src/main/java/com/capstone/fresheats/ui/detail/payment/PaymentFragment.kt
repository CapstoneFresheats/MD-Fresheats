package com.capstone.fresheats.ui.detail.payment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.fresheats.Preferences
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentPaymentBinding
import com.capstone.fresheats.model.request.CheckoutRequest
import com.capstone.fresheats.model.response.checkout.CheckoutResponse
import com.capstone.fresheats.model.response.home.Data
import com.capstone.fresheats.model.response.login.User
import com.capstone.fresheats.ui.detail.DetailActivity
import com.capstone.fresheats.utils.Helpers.formatPrice
import com.google.gson.Gson

class PaymentFragment : Fragment(), PaymentService.View, View.OnClickListener {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataFood : Data
    private lateinit var user : User
    private var loading : Dialog? = null
    lateinit var presenter: PaymentPresenter
    private lateinit var checkoutRequest: CheckoutRequest

    private var total : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as DetailActivity).toolbarPayment()

        dataFood = requireArguments().getParcelable("data")!!
        total = requireArguments().getInt("total")

        val gson = Gson()
        user = gson.fromJson(Preferences.getApp().getUser() , User::class.java)

        presenter = PaymentPresenter(this)

        initViewPayment(dataFood, total!!, user)
        initViewLoading()

        binding.btnCheckout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnCheckout -> {
                actionCheckout(view,total!!)
            }
        }
    }

    private fun initViewPayment(food : Data?, total:Int, user : User){

        food?.apply {
            Glide.with(requireContext())
                .load(picturePathUrl)
                .apply(RequestOptions().override(60, 60))
                .into(binding.ivPoster)

            binding.tvTittle.text = food.name
            binding.tvNameItem.text = food.name
            binding.tvHarga.formatPrice(food.price.toString())
            binding.TvItems.text = "$total Items"

            val totalHarga = food.price!! * total
            binding.tvPrice.formatPrice(totalHarga.toString())

            val priceDriver = 20000
            binding.tvDriver.formatPrice(priceDriver.toString())

            val tax = totalHarga * 10 / 100
            binding.tvTax.formatPrice(tax.toString())

            val paymentTotal = totalHarga + priceDriver + tax
            binding.tvTotal.formatPrice(paymentTotal.toString())

            binding.TvNama.text = user.name
            binding.TvPhoneNo.text = user.phoneNumber
            binding.TvAddress.text = user.address
            binding.TvCity.text = user.city
        }
    }

    private fun actionCheckout(view : View,total : Int){
        view.let{
            val harga = dataFood.price!! * total
            val totalPayment = harga + 20000 + harga.div(10)

            checkoutRequest = CheckoutRequest(
                user.id.toString(),
                dataFood.id.toString(),
                total.toString(),
                totalPayment.toString(),
                "ON_DELIVERY"
            )

            presenter.getCheckout(checkoutRequest,it)
        }
    }

    private fun initViewLoading(){
        loading = Dialog(requireContext())
        val progressBarLayout = layoutInflater.inflate(R.layout.dialog_loader , null)

        loading?.let {
            it.setContentView(progressBarLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: View) {
        (activity as DetailActivity).toolbarDetail()
        Navigation.findNavController(view)
            .navigate(R.id.fragmentPaymentSuccess , null)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(checkoutResponse.paymentUrl)
        startActivity(intent)
    }

    override fun onCheckoutFailed(message: String) {
        Toast.makeText(activity , "Checkout Failed" , Toast.LENGTH_SHORT).show()
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
}