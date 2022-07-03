/*
Copyright 2022 Vasim Mansuri

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.foodiezone.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.foodiezone.R
import com.foodiezone.base.BaseFragment
import com.foodiezone.base.ResponseHandler
import com.foodiezone.databinding.FragmentFoodiedataBinding
import com.foodiezone.di.homeModule
import com.foodiezone.di.loadHomeModule
import com.foodiezone.domain.models.FoodieContent
import com.foodiezone.ui.adapters.FoodieContentListingAdapter
import com.foodiezone.ui.viewmodel.FoodieListingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.unloadKoinModules


class FoodieListingFragment : BaseFragment(R.layout.fragment_foodiedata),
    SwipeRefreshLayout.OnRefreshListener {

    private val scrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val manager = recyclerView.layoutManager as LinearLayoutManager?
                val enabled = manager!!.findFirstCompletelyVisibleItemPosition() == 0
                binding.swipeRefreshLayout.isEnabled = enabled
            }
        }
    private var _binding: FragmentFoodiedataBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FoodieListingViewModel by viewModel()
    private fun injectFeatures() = loadHomeModule
    private val foodieContentListingAdapter: FoodieContentListingAdapter by lazy {
        FoodieContentListingAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFoodiedataBinding.bind(view)
        injectFeatures()
        initUI()
        initListener()
    }

    private fun initUI() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.foodieContentListing.collect { uiState ->
                        when (uiState) {
                            is ResponseHandler.OnFailed -> showError(
                                uiState.exception
                            )
                            is ResponseHandler.OnSuccessResponse -> showFoodieListing(uiState.response)
                            else -> showLoading()
                        }
                    }
                }
            }
        }
    }

    private fun initListener() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener(this@FoodieListingFragment)
            recyclerviewFoodListing.addOnScrollListener(scrollListener)
        }
    }

    override fun showLoading() {
        binding.apply {
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.startShimmer()
            recyclerviewFoodListing.visibility = View.GONE
        }
    }

    override fun hideLoading() {
        binding.apply {
            shimmerLayout.visibility = View.GONE
            recyclerviewFoodListing.visibility = View.VISIBLE
            shimmerLayout.stopShimmer()
        }
    }

    // From here can be customize error handling by screen/custom toast etc..
    override fun showError(exception: Exception) {
        hideLoading()
        super.showError(exception)
    }

    // We can implement paging library here
    private fun showFoodieListing(foodiesList: List<FoodieContent>) {
        hideLoading()
        binding.apply {
            recyclerviewFoodListing.visibility = View.VISIBLE
            foodieContentListingAdapter.showFoodieList = foodiesList
            recyclerviewFoodListing.adapter = foodieContentListingAdapter
        }
    }

    override fun onRefresh() {
        binding.swipeRefreshLayout.isRefreshing = false
        viewModel.fetchFoodieListing(isRequiredToRefreshData = true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        unloadKoinModules(homeModule)
    }
}