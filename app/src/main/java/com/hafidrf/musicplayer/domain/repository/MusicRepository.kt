package com.hafidrf.musicplayer.domain.repository

import com.hafidrf.musicplayer.domain.entity.MusicEntity
import io.reactivex.Single

interface MusicRepository {
    fun searchMusic(query: String): Single<List<MusicEntity>>
}