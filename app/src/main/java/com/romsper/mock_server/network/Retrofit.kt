package com.romsper.mock_server.network

import com.romsper.mock_server.api.MockServerApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    fun getInstance(host: String): MockServerApi {
        return Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MockServerApi::class.java)
    }
}