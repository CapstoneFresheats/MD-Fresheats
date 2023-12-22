package com.capstone.fresheats.ui.home.search

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.ActivitySearchBinding
import com.capstone.fresheats.model.response.home.HomeResponse
import com.capstone.fresheats.ui.home.HomePresenter
import com.capstone.fresheats.ui.home.HomeService

class SearchActivity : AppCompatActivity(), HomeService.View {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var recyclerView: RecyclerView
    private var adapter: SectionRecyclerAdapter? = null
    private lateinit var presenter: HomePresenter
    private var loading: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        presenter = HomePresenter(this)
        presenter.getHome()
    }

    private fun initView() {
        loading = Dialog(this)
        val progressBarLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        loading?.let {
            it.setContentView(progressBarLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // Set span count to 2
        adapter = SectionRecyclerAdapter(this)
        recyclerView.adapter = adapter

        val searchView: SearchView = findViewById(R.id.sv_user)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@SearchActivity, SearchActivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes if needed
                return false
            }
        })

        val btnBackDetail = findViewById<View>(R.id.btnBackDetail)
        btnBackDetail.setOnClickListener {
            onBackButtonClick()
        }
    }

    private fun onBackButtonClick() {
        onBackPressed()
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {
        val allData = homeResponse.data
        adapter?.setData(allData)
    }

    override fun onHomeFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        if (!isFinishing) {
            loading?.show()
        }
    }

    override fun dismissLoading() {
        if (!isFinishing) {
            loading?.dismiss()
        }
    }
}