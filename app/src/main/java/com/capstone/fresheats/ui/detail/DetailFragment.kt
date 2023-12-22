package com.capstone.fresheats.ui.detail

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentDetailBinding
import com.capstone.fresheats.model.response.home.Data
import com.capstone.fresheats.utils.Helpers.formatPrice

class DetailFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var bundle: Bundle
    private lateinit var dataFood: Data
    private var total: Int = 1
    private lateinit var edtTotal: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as DetailActivity).toolbarDetail()

        dataFood = requireActivity().intent.getParcelableExtra("data")!!
        showDetailFood(dataFood)
        edtTotal = binding.edtTotal

        binding.btnOrderNow.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnBackDetail -> {
                (activity as DetailActivity).onBackPressed()
            }
            R.id.btnOrderNow -> {
                val updatedTotal = edtTotal.text.toString().toInt()
                bundle.putInt("total", updatedTotal)
                bundle.putParcelable("data", dataFood)

                Navigation.findNavController(view)
                    .navigate(R.id.fragmentPayment, bundle)
                (activity as DetailActivity).toolbarPayment()
            }
        }
    }


    private fun showDetailFood(food: Data?) {
        bundle = Bundle()
        bundle.putInt("total", total)
        bundle.putParcelable("data", dataFood)

        food?.apply {
            Glide.with(requireContext())
                .load(picturePathUrl)
                .apply(RequestOptions())
                .into(binding.ivPoster)

            binding.tvTitle.text = food.name
            binding.tvDesc.text = food.description
            binding.tvStocks.text = food.stock.toString()
            binding.tvTotal.formatPrice(price.toString())
        }

        binding.edtTotal.text = total.toString().toEditable()

        binding.btnCountPlus.setOnClickListener {
            total += 1
            bundle.putInt("total", total)
            edtTotal.text = total.toString().toEditable()
        }

        binding.btnCountMin.setOnClickListener {
            if (total <= 1) {
                total = 1
            } else {
                total -= 1
            }
            bundle.putInt("total", total)
            edtTotal.text = total.toString().toEditable()
        }

        val cardView = binding.cardView
        cardView.setBackgroundResource(R.drawable.rounded_bottom)
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
