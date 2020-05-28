package com.yanivsos.mixological.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yanivsos.mixological.domain.GetSearchFiltersUseCase
import com.yanivsos.mixological.domain.MultipleFilterDrinkUseCase
import com.yanivsos.mixological.domain.models.DrinkFilter
import com.yanivsos.mixological.domain.models.DrinkPreviewModel
import com.yanivsos.mixological.domain.models.FilterType
import com.yanivsos.mixological.domain.models.SearchFiltersModel
import com.yanivsos.mixological.ui.models.DrinkPreviewUiModel
import com.yanivsos.mixological.ui.models.SearchFiltersUiModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.function.Function

class AdvancedSearchViewModel(
    getSearchFiltersUseCase: GetSearchFiltersUseCase,
    private val filter: MultipleFilterDrinkUseCase,
    mapper: Function<List<DrinkPreviewModel>, List<DrinkPreviewUiModel>>,
    searchMapper: Function<SearchFiltersModel, SearchFiltersUiModel>
) : ViewModel() {

    //todo - combine to 1 use case
    val searchFiltersLiveData: LiveData<SearchFiltersUiModel> = getSearchFiltersUseCase
        .results
        .map { searchMapper.apply(it) }
        .combine(filter.selectedFilters) { searchUiModel: SearchFiltersUiModel,
                                           selectedFilters: Map<FilterType, Set<String>> ->
            val filtersMap = searchUiModel.filters.toMutableMap()
            selectedFilters.forEach { (filter, filterSet) ->
                filtersMap[filter]?.forEach { filter ->
                    filter.selected = filterSet.contains(filter.name)//it.name == key
                    if (filter.selected) {
                        Timber.d("selected filter: $filter, ${filter.name}")
                    }
                }
            }
            SearchFiltersUiModel(
                filters = filtersMap,
                activeFilters = mapToSelectedFilters(selectedFilters)
            )
        }
        .asLiveData()

    private fun mapToSelectedFilters(selectedFilters: Map<FilterType, Set<String>>): Map<FilterType, Int?> {
        return selectedFilters.mapValues {
            val activeSize = it.value.size
            if (activeSize > 0) {
                activeSize
            } else {
                null
            }
        }
    }

    val resultsLiveData = filter
        .filterResults
        .map { mapper.apply(it) }
        .asLiveData()

    fun searchByName(name: String) {
        filter.updateFilter(DrinkFilter(query = name, type = FilterType.NAME))
    }

    fun clearByName() {
        filter.updateFilter(DrinkFilter(query = "", type = FilterType.NAME, active = false))
    }

    private fun clearOnGoingSearches() {
        clearByName()
        filter.cancel()
    }

    fun updateFilter(drinkFilter: DrinkFilter) {
        Timber.i("updating filter: $drinkFilter")
        filter.updateFilter(drinkFilter)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
        clearOnGoingSearches()
    }
}