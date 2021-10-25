package com.hafidrf.musicplayer.utils.ext

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.hafidrf.musicplayer.utils.Utils.getResponseString
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun MockWebServer.enqueueResponse(
    filename:String? = null, responseCode:Int = 200){

    val mockResponse = MockResponse()
    filename?.let {
        mockResponse.setBody(getResponseString(filename))
    }
    mockResponse.setResponseCode(responseCode)
    enqueue(mockResponse)
}

fun <T> MockWebServer.createService(klass:Class<T>):T{
    val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    val retrofit = Retrofit.Builder()
        .baseUrl(url("/"))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(klass)
}

