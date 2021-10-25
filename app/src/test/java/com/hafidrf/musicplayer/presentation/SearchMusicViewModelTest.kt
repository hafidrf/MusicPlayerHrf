package com.hafidrf.musicplayer.presentation

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import com.hafidrf.musicplayer.domain.entity.MusicEntity
import com.hafidrf.musicplayer.domain.usecase.music.SearchMusicUseCase
import com.hafidrf.musicplayer.presentation.music.SearchMusicViewModel
import com.hafidrf.musicplayer.utils.UiState
import com.hafidrf.musicplayer.utils.ext.instantTaskExecutorRule
import io.reactivex.Single
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class SearchMusicViewModelTest: Spek({
    instantTaskExecutorRule()

    lateinit var mViewModel: SearchMusicViewModel

    lateinit var mSearchMusicUseCase: SearchMusicUseCase

    lateinit var musicResultObserver: Observer<UiState<List<MusicEntity>>>

    beforeGroup {
        mSearchMusicUseCase = mock()

        mViewModel = SearchMusicViewModel(mSearchMusicUseCase)

        musicResultObserver = mock()

        mViewModel.musicResult.observeForever(musicResultObserver)
    }

    Feature("search music"){
        Scenario("success search music"){
            
            val result = listOf(MusicEntity(
                songName = "All of me"
            ))

            val query = "jhon"
            Given("shoot results"){
                given { mSearchMusicUseCase(SearchMusicUseCase.Params(query)) }.willReturn(Single.just(result))
            }

            When("call search music") {
                mViewModel.searchMusic(query)
            }

            Then("notify observer"){
                then(musicResultObserver).should().onChanged(UiState.Loading())
                then(musicResultObserver).should().onChanged(UiState.Success(result))
            }
        }
    }
})