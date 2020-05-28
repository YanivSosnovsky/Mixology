package com.yanivsos.mixological.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yanivsos.mixological.R
import com.yanivsos.mixological.extensions.dpToPx
import com.yanivsos.mixological.ui.SpacerItemDecoration
import com.yanivsos.mixological.ui.adapters.DrinkPreviewAdapter
import com.yanivsos.mixological.ui.models.DrinkPreviewUiModel
import com.yanivsos.mixological.ui.models.LandingPageUiModel
import com.yanivsos.mixological.ui.utils.InputActions
import com.yanivsos.mixological.ui.view_model.LandingPageViewModel
import kotlinx.android.synthetic.main.fragment_landing_page.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.android.viewmodel.ext.android.viewModel

class LandingPageFragment : Fragment(R.layout.fragment_landing_page) {

    private val landingPageViewModel: LandingPageViewModel by viewModel()
    private val latestArrivalsAdapter = DrinkPreviewAdapter()
    private val mostPopularAdapter = DrinkPreviewAdapter()
    private val recentSearchesAdapter = DrinkPreviewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeInputActions(latestArrivalsAdapter)
        observeInputActions(mostPopularAdapter)
        observeInputActions(recentSearchesAdapter)
    }

    private fun observeInputActions(adapter: DrinkPreviewAdapter) {
        lifecycleScope.launchWhenStarted {
            adapter
                .inputActions
                .filterIsInstance<InputActions.Click<DrinkPreviewUiModel>>()
                .collect { onDrinkPreviewClicked(it.data) }
        }
        lifecycleScope.launchWhenStarted {
            adapter
                .inputActions
                .filterIsInstance<InputActions.LongClick<DrinkPreviewUiModel>>()
                .collect { onDrinkPreviewLongClicked(it.data) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeLandingPage()
    }

    private fun setupAdapters() {
        val divider = SpacerItemDecoration(right = 16.dpToPx().toInt())
        initDrinkPreviewRecyclerView(latest_arrivals_rv, latestArrivalsAdapter, divider)
        initDrinkPreviewRecyclerView(most_popular_rv, mostPopularAdapter, divider)
        initDrinkPreviewRecyclerView(recent_searches_rv, recentSearchesAdapter, divider)
    }

    private fun initDrinkPreviewRecyclerView(
        recyclerView: RecyclerView,
        adapter: DrinkPreviewAdapter,
        itemDecoration: RecyclerView.ItemDecoration
    ) {
        recyclerView.run {
            this.adapter = adapter
            addItemDecoration(itemDecoration)
        }
    }

    private fun observeLandingPage() {
        landingPageViewModel
            .landingPageLiveData
            .observe(viewLifecycleOwner, Observer { onLandingPageReceived(it) })
    }

    private fun onLandingPageReceived(landingPage: LandingPageUiModel) {
        landingPage.run {
            latestArrivalsAdapter.update(latestArrivals)
            mostPopularAdapter.update(mostPopular)
            recentSearchesAdapter.update(recentSearches)
        }
    }

    private fun onDrinkPreviewClicked(drinkPreviewUiModel: DrinkPreviewUiModel) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDrinkFragment(drinkPreviewUiModel)
        )
    }

    private fun onDrinkPreviewLongClicked(drinkPreviewUiModel: DrinkPreviewUiModel) {
        DrinkPreviewOptionsBottomFragment(drinkPreviewUiModel)
            .show(childFragmentManager)
    }
}