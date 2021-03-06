package com.yanivsos.mixological.v2.landingPage.di

import com.yanivsos.mixological.database.DrinksDatabase
import com.yanivsos.mixological.v2.landingPage.repo.LandingPageRepository
import com.yanivsos.mixological.v2.landingPage.useCases.*
import com.yanivsos.mixological.v2.landingPage.viewModel.LandingPageViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val landingPageDi = module {

    single {
        get<DrinksDatabase>().getLandingPageDao()
    }

    single {
        LandingPageRepository(
            landingPageDao = get(),
            drinkService = get(),
            favoritesDao = get()
        )
    }

    factory {
        GetLatestArrivalsUseCase(
            landingPageRepository = get()
        )
    }

    factory {
        GetMostPopularsUseCase(
            landingPageRepository = get()
        )
    }

    factory {
        GetRecentlyViewedUseCase(
            landingPageRepository = get()
        )
    }

    single {
        GetLandingPagePreviewsUseCase(
            latestArrivalsUseCase = get(),
            mostPopularsUseCase = get(),
            recentlyViewedUseCase = get()
        )
    }

    single {
        RefreshLandingPagePreviewsUseCase(
            landingPageRepository = get()
        )
    }

    factory {
        FetchPreviewsByLetterUseCase(
            drinkRepository = get()
        )
    }

    viewModel {
        LandingPageViewModel(
            getLandingPagePreviewsUseCase = get(),
            refreshLandingPagePreviewsUseCase = get()
        )
    }
}
