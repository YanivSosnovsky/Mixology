package com.zemingo.drinksmenu.ui.models

import android.text.SpannableString
import com.zemingo.drinksmenu.domain.models.AlcoholicFilterModel
import com.zemingo.drinksmenu.domain.models.CategoryModel
import com.zemingo.drinksmenu.domain.models.GlassModel
import com.zemingo.drinksmenu.domain.models.IngredientModel

data class CategoryUiModel(
    val name: String
)

data class DrinkPreviewUiModel(
    val id: String,
    val name: String,
    val thumbnail: String?
)

data class DrinkUiModel(
    val id: String,
    val name: String,
    val instructions: List<SpannableString>,
    val ingredients: List<IngredientUiModel>,
    val category: String,
    val alcoholic: String?,
    val glass: String,
    val video: String?,
    val thumbnail: String?,
    val shareText: String
)

data class LandingPageUiModel(
    val mostPopular: List<DrinkPreviewUiModel>,
    val latestArrivals: List<DrinkPreviewUiModel>,
    val recentSearches: List<DrinkPreviewUiModel>
)

data class IngredientUiModel(
    val name: String,
    val quantity: String?,
    val thumbnail: String? = null
)

data class IngredientFilterUiModel(
    val name: String
)

data class IngredientDetailsUiModel(
    val name: String,
    val description: String?,
    val image: String,
    val isAlcoholic: Boolean,
    val alcoholVolume: String?
)

data class GlassUiModel(
    val name: String
)

data class AlcoholFilterUiModel(
    val name: String
)

data class SearchFiltersUiModel(
    val categories: List<CategoryUiModel> = emptyList(),
    val alcoholic: List<AlcoholFilterUiModel> = emptyList(),
    val ingredients: List<IngredientFilterUiModel> = emptyList(),
    val glasses: List<GlassUiModel> = emptyList()
)