package com.hafidrf.musicplayer.utils

import okio.buffer
import okio.source

object Utils {
    fun getResponseString(filename:String):String{
        val inputStream = javaClass.classLoader
            ?.getResourceAsStream("api-response/${filename}")
        val source = inputStream!!.source().buffer()
        return source.readString(Charsets.UTF_8)
    }
}
