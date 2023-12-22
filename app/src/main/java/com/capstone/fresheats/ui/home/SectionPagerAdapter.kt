package com.capstone.fresheats.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.fresheats.model.response.home.Data
import com.capstone.fresheats.ui.home.vegetables.HomeVegetableFragment
import com.capstone.fresheats.ui.home.Fruits.HomeFruitsFragment
import com.capstone.fresheats.ui.home.meat.HomeMeatFragment

class SectionPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    var VegetablesList: ArrayList<Data>? = ArrayList()
    var FruitsList: ArrayList<Data>? = ArrayList()
    var MeatsList: ArrayList<Data>? = ArrayList()

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = HomeVegetableFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", VegetablesList)
                fragment.arguments = bundle
                fragment
            }
            1 -> {
                val fragment = HomeFruitsFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", FruitsList)
                fragment.arguments = bundle
                fragment
            }
            2 -> {
                val fragment = HomeMeatFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", MeatsList)
                fragment.arguments = bundle
                fragment
            }
            else -> {
                val fragment = HomeVegetableFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", VegetablesList)
                fragment.arguments = bundle
                fragment
            }
        }
    }

    fun setData(
        VegetablesListparms: ArrayList<Data>?,
        FruitsListparms: ArrayList<Data>?,
        MeatsListparms: ArrayList<Data>?
    ) {
        VegetablesList = VegetablesListparms
        FruitsList = FruitsListparms
        MeatsList = MeatsListparms
    }
}
