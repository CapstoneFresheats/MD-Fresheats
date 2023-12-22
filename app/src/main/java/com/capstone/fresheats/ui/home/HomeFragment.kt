package com.capstone.fresheats.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.capstone.fresheats.Preferences
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentHomeBinding
import com.capstone.fresheats.model.response.home.Data
import com.capstone.fresheats.model.response.home.HomeResponse
import com.capstone.fresheats.ui.home.search.SearchActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(), HomeService.View {

    private lateinit var binding: FragmentHomeBinding

    private var VegetablesList: ArrayList<Data> = ArrayList()
    private var FruitsList: ArrayList<Data> = ArrayList()
    private var MeatsList: ArrayList<Data> = ArrayList()

    private lateinit var presenter: HomePresenter
    private var loading: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        presenter = HomePresenter(this)
        presenter.getHome()
    }

    private fun initView() {
        loading = Dialog(binding.root.context)
        val progressBarLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        loading?.let {
            it.setContentView(progressBarLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        val shimmerHorizontalScrollView: ShimmerFrameLayout = binding.shimmerHorizontalScrollView
        shimmerHorizontalScrollView.startShimmer()

        val handler = Handler()
        handler.postDelayed({
            binding.shimmerHorizontalScrollView.visibility = View.GONE
            binding.shimmerHorizontalScrollView.alpha = 0f
            binding.shimmerHorizontalScrollView.stopShimmer()
        }, 3000)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val sectionPagerAdapter = SectionPagerAdapter(requireActivity())
        sectionPagerAdapter.setData(VegetablesList, FruitsList, MeatsList)
        viewPager.adapter = sectionPagerAdapter

        val tabLayout: TabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Sayur"
                1 -> "Buah"
                2 -> "Daging"
                else -> ""
            }
        }.attach()

        val searchView: SearchView = requireView().findViewById(R.id.sv_user)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(requireContext(), SearchActivity::class.java)
                intent.putExtra("query", query)  // Pass the query to the SearchActivity
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loading?.dismiss()
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        VegetablesList.clear()
        FruitsList.clear()
        MeatsList.clear()

        for (a in homeResponse.data.indices) {
            val items: List<String> = homeResponse.data[a].types?.split(",") ?: ArrayList()
            for (x in items.indices) {
                when {
                    items[x].equals("Sayur", true) -> VegetablesList.add(homeResponse.data[a])
                    items[x].equals("Daging", true) -> MeatsList.add(homeResponse.data[a])
                    items[x].equals("Buah", true) -> FruitsList.add(homeResponse.data[a])
                }
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(requireActivity())
        sectionPagerAdapter.setData(VegetablesList, FruitsList, MeatsList)
        binding.viewPager.adapter = sectionPagerAdapter
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
}