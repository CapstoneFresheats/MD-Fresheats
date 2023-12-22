package com.capstone.fresheats.ui.order.inprogress

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.fresheats.databinding.ItemInprogressBinding
import com.capstone.fresheats.model.response.transaction.DataTransaction
import com.capstone.fresheats.utils.Helpers.formatPrice

class InprogressAdapter(
    private var listData: List<DataTransaction>,
    private val itemAdapterCallback: InprogressFragment,
) : RecyclerView.Adapter<InprogressAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemInprogressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(private val binding: ItemInprogressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataTransaction: DataTransaction, itemAdapterCallback: ItemAdapterCallback) {
            binding.apply {

                Glide.with(itemView.context)
                    .load(dataTransaction.ingredients.picturePathUrl)
                    .apply(RequestOptions().override(60,60))
                    .into(ivPoster)

                tvTitle.text = dataTransaction.ingredients.name
                tvPrice.formatPrice(dataTransaction.ingredients.price.toString())
                tvTotal.text = "${dataTransaction.quantity} Items"

                dataTransaction.status.equals("ON_DELIVERY" , true)
                tvStatus.visibility = View.VISIBLE
                tvStatus.setTextColor(Color.parseColor("#52FC02"))
                tvStatus.text = "Dalam Perjalanan"

                itemView.setOnClickListener { itemAdapterCallback.onClick(itemView, dataTransaction) }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick(v: View, dataTransaction: DataTransaction)
    }
}