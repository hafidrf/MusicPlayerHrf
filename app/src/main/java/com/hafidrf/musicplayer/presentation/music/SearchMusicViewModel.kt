package com.hafidrf.musicplayer.presentation.music

import androidx.lifecycle.MutableLiveData
import com.hafidrf.musicplayer.domain.entity.MusicEntity
import com.hafidrf.musicplayer.domain.usecase.music.SearchMusicUseCase
import com.hafidrf.musicplayer.presentation.base.BaseViewModel
import com.hafidrf.musicplayer.utils.UiState

class SearchMusicViewModel(
    val mSearchMusicUseCase: SearchMusicUseCase,
): BaseViewModel() {

    val musicResult = MutableLiveData<UiState<List<MusicEntity>>>()

    fun searchMusic(artistName: String){
        musicResult.value = UiState.Loading()
        val params = SearchMusicUseCase.Params(artistName)
        compositeDisposable.add(mSearchMusicUseCase(params)
            .subscribe({
                musicResult.value = UiState.Success(it)
            },{
                musicResult.value = UiState.Error(it)
            }))
    }

}