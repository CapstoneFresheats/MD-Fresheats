package com.capstone.fresheats.ui.order

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentOrdersBinding
import com.capstone.fresheats.model.response.transaction.DataTransaction
import com.capstone.fresheats.model.response.transaction.TransactionResponse
import com.capstone.fresheats.ui.auth.AuthActivity

class OrderFragment : Fragment(), OrderService.View, View.OnClickListener {

    lateinit var presenter: OrderPresenter

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private var loading: Dialog? = null

    var inprogressList: ArrayList<DataTransaction>? = ArrayList()
    var pastorderList: ArrayList<DataTransaction>? = ArrayList()

    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewLoading()
        presenter = OrderPresenter(this)
        presenter.getTransaction()

        val btnFind: Button = binding.root.findViewById(R.id.btnfind)
        btnFind.setOnClickListener(this)

        clearDataInViewPager()
    }

    private fun clearDataInViewPager() {
        inprogressList?.clear()
        pastorderList?.clear()

        val adapter = binding.viewPager.adapter as? SectionPagerAdapter
        adapter?.clearFragments()
    }

    private fun initViewLoading() {
        loading = Dialog(requireContext())
        val progressBarLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        loading?.let {
            it.setContentView(progressBarLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onTransactionSuccess(transactionResponse: TransactionResponse) {
        if (transactionResponse.data.isEmpty()) {
            binding.llEmpty.visibility = View.VISIBLE
            binding.tabLayout.visibility = View.GONE

            val toolbar: Toolbar = binding.root.findViewById(R.id.toolbar)
            toolbar.visibility = View.GONE
        } else {
            inprogressList?.clear()
            pastorderList?.clear()

            for (a in transactionResponse.data.indices) {
                when {
                    transactionResponse.data[a].status.equals("ON_DELIVERY", true) -> {
                        inprogressList?.add(transactionResponse.data[a])
                    }

                    transactionResponse.data[a].status.equals("DELIVERED", true)
                            || transactionResponse.data[a].status.equals("CANCELLED", true)
                            || transactionResponse.data[a].status.equals("SUCCESS", true) -> {

                        if (transactionResponse.data[a].status.equals("CANCELLED", true)
                            && inprogressList?.contains(transactionResponse.data[a]) == true
                        ) {
                            inprogressList?.remove(transactionResponse.data[a])
                            pastorderList?.add(transactionResponse.data[a])
                        } else {
                            pastorderList?.add(transactionResponse.data[a])
                        }
                    }
                }
            }

            val sectionPagerAdapter = SectionPagerAdapter(
                childFragmentManager
            )
            sectionPagerAdapter.setData(inprogressList, pastorderList)
            binding.viewPager.adapter = sectionPagerAdapter
            binding.tabLayout.setupWithViewPager(binding.viewPager)

            val adapter = binding.viewPager.adapter as? SectionPagerAdapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onTransactionFailed(message: String) {

    }

    override fun showLoading() {
        if (isAdded && isVisible && !requireActivity().isFinishing && !isStateSaved) {
            loading?.show()
        }
    }

    override fun dismissLoading() {
        if (isAdded && isVisible && !requireActivity().isFinishing) {
            loading?.dismiss()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnfind -> {
                val intent = Intent(requireContext(), AuthActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun clearDataAndViewPager() {
        clearDataInViewPager()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}