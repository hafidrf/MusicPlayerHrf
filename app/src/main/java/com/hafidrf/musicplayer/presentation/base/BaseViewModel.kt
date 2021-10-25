package com.hafidrf.musicplayer.presentation.base

import androidx.lifecycle.ViewModel
import com.github.ajalt.timberkt.d
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        d { "vm cleared" }
        compositeDisposable.dispose()
    }

}