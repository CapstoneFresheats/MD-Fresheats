package com.capstone.fresheats.ui.order.pastorders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.fresheats.databinding.FragmentPastordersBinding
import com.capstone.fresheats.model.response.transaction.DataTransaction
import com.capstone.fresheats.ui.order.pastorders.detail.DetailPastorderActivity

class PastordersFragment : Fragment(), PastorderAdapter.ItemAdapterCallback {

    private var _binding: FragmentPastordersBinding? = null
    private val binding get() = _binding!!

    private var adapter: PastorderAdapter? = null
    private var pastordersList:ArrayList<DataTransaction>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPastordersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pastordersList = arguments?.getParcelableArrayList("data")

        if(!pastordersList.isNullOrEmpty()){
            adapter = PastorderAdapter(pastordersList!!, this)
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
        val intent = Intent(activity , DetailPastorderActivity::class.java)
        intent.putExtra("data" , dataTransaction)
        startActivity(intent)
    }
}