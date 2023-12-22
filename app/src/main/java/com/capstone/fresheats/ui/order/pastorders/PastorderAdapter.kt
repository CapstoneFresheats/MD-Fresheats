package com.capstone.fresheats.ui.order.pastorders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.fresheats.databinding.ItemPastordersBinding
import com.capstone.fresheats.model.response.transaction.DataTransaction
import com.capstone.fresheats.utils.Helpers.formatDate
import com.capstone.fresheats.utils.Helpers.formatPrice

class PastorderAdapter(
    private var listData: List<DataTransaction>,
    private val itemAdapterCallback: ItemAdapterCallback,
) : RecyclerView.Adapter<PastorderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPastordersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(private val binding: ItemPastordersBinding) :
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
                tvDate.formatDate(dataTransaction.createdAt)

                if(dataTransaction.status.equals("CANCELLED", true)){
                    tvStatus.visibility = View.VISIBLE
                    tvStatus.setTextColor(Color.parseColor("#dc143c"))
                    tvStatus.text = "Pesanan Dibatalkan"
                }else if(dataTransaction.status.equals("DELIVERED" , true)){
                    tvStatus.visibility = View.VISIBLE
                    tvStatus.setTextColor(Color.parseColor("#52FC02"))
                    tvStatus.text = "Pesanan Diterima"
                }

                itemView.setOnClickListener { itemAdapterCallback.onClick(itemView, dataTransaction) }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick(v: View, dataTransaction: DataTransaction)
    }
}