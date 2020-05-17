package com.zemingo.drinksmenu.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.zemingo.drinksmenu.domain.AddToWatchlistUseCase
import com.zemingo.drinksmenu.domain.GetDrinkPreviewUseCase
import com.zemingo.drinksmenu.domain.RemoveFromWatchlistUseCase
import com.zemingo.drinksmenu.domain.models.DrinkPreviewModel
import com.zemingo.drinksmenu.domain.models.WatchlistItemModel
import com.zemingo.drinksmenu.ui.models.DrinkPreviewUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.function.Function

class DrinkPreviewOptionsViewModel(
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val removeFromWatchlistUseCase: RemoveFromWatchlistUseCase,
    getDrinkPreviewUseCase: GetDrinkPreviewUseCase,
    mapper: Function<List<DrinkPreviewModel>, List<DrinkPreviewUiModel>>,
    id: String
) : ViewModel() {

    val drinkLiveData: LiveData<DrinkPreviewUiModel> =
        getDrinkPreviewUseCase
            .getById(id)
            .filter { it.isNotEmpty() }
            .map { mapper.apply(it) }
            .map { it.first() }
            .asLiveData()

    fun addToWatchlist(id: String) {
        GlobalScope.launch(Dispatchers.IO) {
            addToWatchlistUseCase.addToWatchlist(
                WatchlistItemModel(id)
            )
        }
    }

    fun removeFromWatchlist(id: String) {
        GlobalScope.launch {
            removeFromWatchlistUseCase.remove(id)
        }
    }
}