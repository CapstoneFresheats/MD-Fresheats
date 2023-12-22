package com.capstone.fresheats.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.fresheats.databinding.ItemHomeHorizontalBinding
import com.capstone.fresheats.model.response.home.Data
import com.capstone.fresheats.utils.Helpers.formatPrice

class HomeListAdapter(
    private val listData: List<Data>,
    private val itemAdapterCallback: ItemAdapterCallback,
) : RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeHorizontalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(private val binding: ItemHomeHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            binding.apply {
                tvTitle.text = data.name
                tvPrice.formatPrice(data.price.toString())
                rbFood.rating = data.rate?.toFloat() ?: 0f

                Glide.with(itemView.context)
                    .load(data.picturePathUrl)
                    .into(ivPoster)

                itemView.setOnClickListener { itemAdapterCallback.onClick(itemView, data) }
            }
        }
    }

    interface ItemAdapterCallback {
        fun onClick(v: View, data: Data)
    }
}