package com.capstone.fresheats.ui.order.inprogress

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.fresheats.databinding.FragmentInprogressBinding
import com.capstone.fresheats.model.response.transaction.DataTransaction
import com.capstone.fresheats.ui.order.inprogress.detail.DetailInProgressActivity

class InprogressFragment : Fragment(), InprogressAdapter.ItemAdapterCallback {

    private var _binding: FragmentInprogressBinding? = null
    private val binding get() = _binding!!

    private var adapter: InprogressAdapter? = null
    private var inprogressList:ArrayList<DataTransaction>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentInprogressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        inprogressList = arguments?.getParcelableArrayList("data")

        if(!inprogressList.isNullOrEmpty()){
            adapter = InprogressAdapter(inprogressList!!, this)
            val layoutManager = LinearLayoutManager(activity)
            binding.rcList.layoutManager = layoutManager
            binding.rcList.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View, dataTransaction: DataTransaction) {
        var intent = Intent(activity , DetailInProgressActivity::class.java)
        intent.putExtra("data" , dataTransaction)
        startActivity(intent)
    }
}