package com.hafidrf.musicplayer.utils

sealed class UiState<T> {
    data class Loading<T>(val isLoading: Boolean = true) : UiState<T>()
    data class Success<T>(val data : T) : UiState<T>()
    data class Error<T>(val throwable: Throwable) : UiState<T>()
}