package com.hafidrf.musicplayer.data.repository

import com.hafidrf.musicplayer.data.remote.service.MusicService
import com.hafidrf.musicplayer.domain.entity.MusicEntity
import com.hafidrf.musicplayer.domain.repository.MusicRepository
import io.reactivex.Single

class MusicRepositoryImpl(private val musicService: MusicService): MusicRepository {

    override fun searchMusic(query: String): Single<List<MusicEntity>> {
        return musicService.searchMusic(query).map { it.results.map { it.toEntity() } }
    }

}