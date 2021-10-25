package com.hafidrf.musicplayer.data.remote.response

import com.google.gson.annotations.SerializedName
import com.hafidrf.musicplayer.domain.entity.MusicEntity


data class SearchMusicResultResponse(
    @SerializedName("resultCount")
    val resultCount: Int = 0,
    @SerializedName("results")
    val results: List<Result> = listOf()
)

data class Result(
    @SerializedName("wrapperType")
    val wrapperType: String = "",
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("collectionId")
    val collectionId: Int = 0,
    @SerializedName("trackId")
    val trackId: Int = 0,
    @SerializedName("artistName")
    val artistName: String = "",
    @SerializedName("collectionName")
    val collectionName: String = "",
    @SerializedName("trackName")
    val trackName: String = "",
    @SerializedName("collectionCensoredName")
    val collectionCensoredName: String = "",
    @SerializedName("trackCensoredName")
    val trackCensoredName: String = "",
    @SerializedName("collectionArtistId")
    val collectionArtistId: Int = 0,
    @SerializedName("collectionArtistViewUrl")
    val collectionArtistViewUrl: String = "",
    @SerializedName("collectionViewUrl")
    val collectionViewUrl: String = "",
    @SerializedName("trackViewUrl")
    val trackViewUrl: String = "",
    @SerializedName("previewUrl")
    val previewUrl: String = "",
    @SerializedName("artworkUrl30")
    val artworkUrl30: String = "",
    @SerializedName("artworkUrl60")
    val artworkUrl60: String = "",
    @SerializedName("artworkUrl100")
    val artworkUrl100: String = "",
    @SerializedName("collectionPrice")
    val collectionPrice: Double = 0.0,
    @SerializedName("trackPrice")
    val trackPrice: Double = 0.0,
    @SerializedName("trackRentalPrice")
    val trackRentalPrice: Double = 0.0,
    @SerializedName("collectionHdPrice")
    val collectionHdPrice: Double = 0.0,
    @SerializedName("trackHdPrice")
    val trackHdPrice: Double = 0.0,
    @SerializedName("trackHdRentalPrice")
    val trackHdRentalPrice: Double = 0.0,
    @SerializedName("releaseDate")
    val releaseDate: String = "",
    @SerializedName("collectionExplicitness")
    val collectionExplicitness: String = "",
    @SerializedName("trackExplicitness")
    val trackExplicitness: String = "",
    @SerializedName("discCount")
    val discCount: Int = 0,
    @SerializedName("discNumber")
    val discNumber: Int = 0,
    @SerializedName("trackCount")
    val trackCount: Int = 0,
    @SerializedName("trackNumber")
    val trackNumber: Int = 0,
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Int = 0,
    @SerializedName("country")
    val country: String = "",
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("primaryGenreName")
    val primaryGenreName: String = "",
    @SerializedName("contentAdvisoryRating")
    val contentAdvisoryRating: String = "",
    @SerializedName("shortDescription")
    val shortDescription: String = "",
    @SerializedName("longDescription")
    val longDescription: String = "",
    @SerializedName("hasITunesExtras")
    val hasITunesExtras: Boolean = false,
    @SerializedName("artistId")
    val artistId: Int = 0,
    @SerializedName("artistViewUrl")
    val artistViewUrl: String = "",
    @SerializedName("isStreamable")
    val isStreamable: Boolean = false,
    @SerializedName("collectionArtistName")
    val collectionArtistName: String = ""
) {
    fun toEntity() = MusicEntity(
        artWorkUrl = artworkUrl100,
        songName = trackName,
        albumName = collectionName,
        artisName = artistName,
        musicUrl = previewUrl
    )
}