package com.romsper.mock_server.network

import com.romsper.mock_server.api.MockServerApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient.Builder()
        .build()

object Retrofit {
    fun getInstance(host: String): MockServerApi {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(host)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MockServerApi::class.java)
    }
}