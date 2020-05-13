package com.zemingo.drinksmenu.domain.di

import com.zemingo.drinksmenu.domain.*
import org.koin.dsl.module

val useCasesModule = module {

    factory {
        GetCategoriesUseCase(
            repository = get()
        )
    }

    factory {
        FetchIngredientsUseCase(
            repository = get()
        )
    }

    factory {
        GetIngredientsUseCase(
            repository = get()
        )
    }

    factory {
        GetDrinkPreviewByCategoryUseCase(
            repository = get()
        )
    }

    factory {
        GetDrinkPreviewUseCase(
            repository = get()
        )
    }

    factory {
        GetPreviousSearchResultsUseCase(
            searchRepository = get()
        )
    }

    factory {
        MarkAsSearchedDrinkPreviewUseCase(
            repository = get()
        )
    }

    factory {
        GetMostPopularUseCase(
            repository = get()
        )
    }

    factory {
        GetLatestArrivalsUseCase(
            repository = get()
        )
    }

    factory { (id: String) ->
        GetDrinkUseCase(
            repository = get(),
            id = id
        )
    }

    factory { (ingredientName: String) ->
        GetIngredientDetailsUseCase(
            repository = get(),
            ingredientName = ingredientName
        )
    }

    factory {
        FilterDrinkUseCase(
            advancedSearchRepository = get()
        )
    }

    factory {
        MultipleFilterDrinkUseCase(
            getDrinkPreviewUseCase = get(),
            alcoholicFilter = get(),
            categoryFilter = get(),
            ingredientFilter = get(),
            glassFilter = get(),
            nameFilter = get()
        )
    }

    factory {
        GetGlassesUseCase(
            repository = get()
        )
    }

    factory {
        GetAlcoholicFiltersUseCase(
            repository = get()
        )
    }

    factory {
        GetSearchFiltersUseCase(
            getAlcoholicFiltersUseCase = get(),
            getCategoriesUseCase = get(),
            getGlassesUseCase = get(),
            getIngredientsUseCase = get()
        )
    }

    factory {
        FetchAllPreviewsUseCase(
            previewRepository = get()
        )
    }

}