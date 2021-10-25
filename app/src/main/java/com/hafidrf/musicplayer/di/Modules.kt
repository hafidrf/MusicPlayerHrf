package com.hafidrf.musicplayer.di

import com.hafidrf.musicplayer.data.remote.createWebService
import com.hafidrf.musicplayer.data.remote.provideOkHttpClient
import com.hafidrf.musicplayer.data.remote.service.MusicService
import com.hafidrf.musicplayer.data.repository.MusicRepositoryImpl
import com.hafidrf.musicplayer.domain.repository.MusicRepository
import com.hafidrf.musicplayer.domain.usecase.music.SearchMusicUseCase
import com.hafidrf.musicplayer.presentation.music.SearchMusicViewModel
import com.hafidrf.musicplayer.utils.rx.AppSchedulerProvider
import com.hafidrf.musicplayer.utils.rx.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideOkHttpClient() }
    single { createWebService<MusicService>(get()) }

    single<SchedulerProvider> { AppSchedulerProvider() }

}

val dataModule = module {
    single<MusicRepository> { MusicRepositoryImpl(get()) }
}

val domainModule = module {
    single { SearchMusicUseCase(get(), get()) }
}

val viewModelModule = module {
    viewModel { SearchMusicViewModel(get()) }
}

val myAppModule = listOf(appModule, dataModule, domainModule, viewModelModule)