package com.capstone.fresheats.ui.order.inprogress.detail

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.fresheats.Preferences
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.ActivityDeatilInProgressBinding
import com.capstone.fresheats.model.request.UpdateTransactionRequest
import com.capstone.fresheats.model.response.home.Data
import com.capstone.fresheats.model.response.login.User
import com.capstone.fresheats.model.response.transaction.DataTransaction
import com.capstone.fresheats.model.response.transaction.updateTransaction.TransactionUpdateResponse
import com.capstone.fresheats.utils.Helpers.formatPrice
import com.google.gson.Gson

class DetailInProgressActivity : AppCompatActivity(), UpdateTransactionService.View {

    private lateinit var binding: ActivityDeatilInProgressBinding
    private lateinit var user: User
    private var loading: Dialog? = null
    lateinit var presenter: DetailInProgressPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeatilInProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataTransaction =
            intent.getParcelableExtra<DataTransaction>("data")!!

        val gson = Gson()
        user = gson.fromJson(Preferences.getApp().getUser(), User::class.java)

        initViewLoading()

        presenter = DetailInProgressPresenter(this)

        showDetailTransactionView(dataTransaction)
    }

    private fun showDetailTransactionView(dataTransaction: DataTransaction) {

        val dataDetail = Data(
            dataTransaction.ingredients.createdAt,
            dataTransaction.ingredients.deletedAt,
            dataTransaction.ingredients.description,
            dataTransaction.ingredients.id,
            dataTransaction.ingredients.name,
            dataTransaction.ingredients.picturePath,
            dataTransaction.ingredients.picturePathUrl,
            dataTransaction.ingredients.price,
            dataTransaction.ingredients.rate,
            dataTransaction.ingredients.stock,
            dataTransaction.ingredients.types,
            dataTransaction.ingredients.updatedAt
        )

        dataDetail.apply {
            Glide.with(this@DetailInProgressActivity)
                .load(picturePathUrl)
                .apply(RequestOptions().override(60, 60))
                .into(binding.ivPoster)

            binding.tvName.text = name
            binding.tvNameDetail.text = name
            binding.tvPrice.formatPrice(price.toString())
            binding.tvTotalItem.text = "${dataTransaction.quantity} Items"

            var totalHarga = price!! * dataTransaction.quantity
            binding.tvPrice.formatPrice(totalHarga.toString())

            var priceDriver = 20000
            binding.tvPriceDriver.formatPrice(priceDriver.toString())

            var tax = totalHarga * 10 / 100
            binding.tvPriceTax.formatPrice(tax.toString())

            var paymentTotal = totalHarga + priceDriver + tax
            binding.tvTotalPayment.formatPrice(paymentTotal.toString())

            binding.tvNameUser.text = user.name
            binding.tvPhoneUser.text = user.phoneNumber
            binding.tvAddresUser.text = user.address
            binding.tvCityUser.text = user.city

            binding.tvStatus.text = dataTransaction.status

            if (dataTransaction.status.equals("CANCELLED", true)) {
                binding.tvStatus.text = "Pesanan Dibatalkan"
                binding.tvStatus.setTextColor(Color.parseColor("#dc143c"))
            } else if (dataTransaction.status.equals("DELIVERED", true)) {
                binding.tvStatus.text = "Pesanan Diterima"
                binding.tvStatus.setTextColor(Color.parseColor("#1ABC9C"))
            } else if (dataTransaction.status.equals("ON_DELIVERY", true)) {
                binding.tvStatus.text = "Dalam Perjalanan"
                binding.tvStatus.setTextColor(Color.parseColor("#1976D2"))
            }
        }

        binding.btnCheckFood.setOnClickListener {
            showValidationAlertDialog(dataTransaction)
        }
    }

    private fun showValidationAlertDialog(dataTransaction2: DataTransaction) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to update the transaction status?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            performTransactionUpdate()

        }
        alertDialogBuilder.setNegativeButton("No") { _, _ ->

        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun performTransactionUpdate() {
        val dataTransaction = intent.getParcelableExtra<DataTransaction>("data")!!
        val updateTransactionRequest = UpdateTransactionRequest("CANCELLED")

        val id = dataTransaction.id


        presenter.performTransactionUpdate(id, updateTransactionRequest, binding.root)
    }

    private fun initViewLoading() {
        loading = Dialog(this)
        val progressBarLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        loading?.let {
            it.setContentView(progressBarLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun showTransactionUpdateSuccess(dataTransaction2: TransactionUpdateResponse?) {

        Toast.makeText(this,"Pesanan Dibatalkan Silahkan Restart Aplikasi Anda", Toast.LENGTH_LONG).show()
    }

    override fun showTransactionUpdateError(message: String) {

    }

    override fun showLoading() {
        loading?.show()
    }

    override fun dismissLoading() {
        loading?.dismiss()
    }
}