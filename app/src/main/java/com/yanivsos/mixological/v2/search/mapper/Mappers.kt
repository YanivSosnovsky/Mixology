package com.yanivsos.mixological.v2.search.mapper

import com.yanivsos.mixological.database.AlcoholicFilterModel
import com.yanivsos.mixological.database.CategoryModel
import com.yanivsos.mixological.database.GlassModel
import com.yanivsos.mixological.database.IngredientModel
import com.yanivsos.mixological.network.response.*
import com.yanivsos.mixological.v2.search.useCases.AccumulativeOperator
import com.yanivsos.mixological.v2.search.view.FilterChoiceGroupView

fun DrinksWrapperResponse<IngredientResponse>.toIngredientsModel(): List<IngredientModel> {
    return data.map {
        IngredientModel(
            name = it.name
        )
    }
}

fun DrinksWrapperResponse<CategoryResponse>.toCategoriesModel(): List<CategoryModel> {
    return data.map {
        CategoryModel(
            name = it.category
        )
    }
}

fun DrinksWrapperResponse<GlassResponse>.toGlassesModel(): List<GlassModel> {
    return data
        .mapNotNull { it.strGlass }
        .filter { it.isNotEmpty() }
        .map { GlassModel(it) }
}

fun DrinksWrapperResponse<AlcoholicFilterResponse>.toAlcoholicsModel(): List<AlcoholicFilterModel> {
    return data
        .mapNotNull { it.strAlcoholic }
        .filterNot { it.isBlank() }
        .map {
            AlcoholicFilterModel(
                name = it
            )
        }
}

fun AccumulativeOperator.toFilterChoice(): FilterChoiceGroupView.SelectedFilterChoice {
    return when (this) {
        AccumulativeOperator.Intersection -> FilterChoiceGroupView.SelectedFilterChoice.And
        AccumulativeOperator.Union -> FilterChoiceGroupView.SelectedFilterChoice.Or
    }
}
