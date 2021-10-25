package com.hafidrf.musicplayer.domain.usecase.music

import com.hafidrf.musicplayer.domain.repository.MusicRepository
import com.hafidrf.musicplayer.domain.entity.MusicEntity
import com.hafidrf.musicplayer.domain.usecase.SingleUseCase
import com.hafidrf.musicplayer.utils.rx.SchedulerProvider
import io.reactivex.Single

class SearchMusicUseCase(
    private val mMusicRepository: MusicRepository,
    mSchedulerProvider: SchedulerProvider
): SingleUseCase<List<MusicEntity>, SearchMusicUseCase.Params>(mSchedulerProvider) {

    override fun execute(params: Params?): Single<List<MusicEntity>> {
        requireNotNull(params, { "params can't be null"})
        return mMusicRepository.searchMusic(params.artist)
    }

    data class Params(
        val artist: String
    )


}