package com.hafidrf.musicplayer.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


typealias Inflate<T> = (LayoutInflater) -> T

abstract class BaseViewBindingActivity<VB: ViewBinding>(
    private val inflate: Inflate<VB>
): AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(_binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}