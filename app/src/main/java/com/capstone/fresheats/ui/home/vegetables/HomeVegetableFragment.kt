package com.capstone.fresheats.ui.home.vegetables

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.fresheats.databinding.FragmentHomeListBinding
import com.capstone.fresheats.model.response.home.Data
import com.capstone.fresheats.ui.detail.DetailActivity
import com.capstone.fresheats.ui.home.HomeListAdapter

class HomeVegetableFragment : Fragment(), HomeListAdapter.ItemAdapterCallback {

    private var _binding: FragmentHomeListBinding? = null
    private val binding get() = _binding!!

    private var VegetablesList: ArrayList<Data>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        VegetablesList = arguments?.getParcelableArrayList("data")

        VegetablesList?.let {
            Log.d("HomeNewTasteFragment", "Data size: ${it.size}")
            val adapter = HomeListAdapter(it, this)
            val layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            binding.rcList.layoutManager = layoutManager
            binding.rcList.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View, data: Data) {
        val detailIntent = Intent(activity, DetailActivity::class.java)
        detailIntent.putExtra("data", data)
        startActivity(detailIntent)
    }
}
