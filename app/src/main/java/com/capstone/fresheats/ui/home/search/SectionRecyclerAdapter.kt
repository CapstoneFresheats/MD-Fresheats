package com.capstone.fresheats.ui.home.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.ItemHomeGridlayoutBinding
import com.capstone.fresheats.model.response.home.Data
import com.capstone.fresheats.utils.Helpers.formatPrice

class SectionRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<SectionRecyclerAdapter.SectionViewHolder>() {

    private var allData: List<Data> = ArrayList()

    fun setData(data: List<Data>) {
        this.allData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_home_gridlayout, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val data = allData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHomeGridlayoutBinding.bind(itemView)

        fun bind(data: Data) {
            binding.apply {
                tvTitle.text = data.name
                tvPrice.formatPrice(data.price.toString())
                rbFood.rating = data.rate?.toFloat() ?: 0f

                Glide.with(itemView.context)
                    .load(data.picturePathUrl)
                    .placeholder(R.drawable.ic_apple)
                    .error(R.drawable.ic_apple)
                    .into(ivPoster)
            }
        }
    }
}