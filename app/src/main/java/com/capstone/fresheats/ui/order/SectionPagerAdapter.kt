package com.capstone.fresheats.ui.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.capstone.fresheats.model.response.transaction.DataTransaction
import com.capstone.fresheats.ui.order.inprogress.InprogressFragment
import com.capstone.fresheats.ui.order.pastorders.PastordersFragment

class SectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var inprogressList:ArrayList<DataTransaction>? = ArrayList()
    private var pastordersList:ArrayList<DataTransaction>? = ArrayList()
    private val fragments: MutableList<Fragment> = ArrayList()
    private val fragmentTitles: MutableList<String> = ArrayList()

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position){
            0 -> "Sedang Dipesan"
            1 -> "Pesanan Sebelumnya"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        val fragment : Fragment
        fragments.clear()
        return when(position){
            0 ->{
                fragment = InprogressFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data" , inprogressList)
                fragment.arguments = bundle
                return fragment
            }
            1 ->{
                fragment = PastordersFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data" , pastordersList)
                fragment.arguments = bundle
                return fragment
            }
            else ->{
                fragment = InprogressFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data" , inprogressList)
                fragment.arguments = bundle
                return fragment
            }
        }
    }

    fun setData(inprogresssParams: ArrayList<DataTransaction>?, pastOrderParams: ArrayList<DataTransaction>?) {
        inprogressList = inprogresssParams
        pastordersList = pastOrderParams

        fragments.clear()

        fragments.add(InprogressFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("data", inprogressList)
            }
        })

        fragments.add(PastordersFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("data", pastordersList)
            }
        })

        notifyDataSetChanged()
    }

    fun clearFragments() {
        fragments.clear()
        fragmentTitles.clear()
        notifyDataSetChanged()
    }
}