package com.romsper.mock_server.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Query

interface MockServerApi {

    @PUT("expectation")
    fun createMock(@Body body: Any): Call<ResponseBody>

    @PUT("verify")
    fun verify(@Body body: Any): Call<ResponseBody>

    @PUT("verifySequence")
    fun verifySequence(@Body body: Any): Call<ResponseBody>

    @PUT("clear")
    fun clear(@Body body: Any): Call<ResponseBody>

    @PUT("reset")
    fun reset(): Call<ResponseBody>

    @PUT("retrieve")
    fun retrieve(@Body body: Any,
                 @Query ("type") type: String,
                 @Query ("format") format: String?): Call<ResponseBody>

    @PUT("status")
    fun status(): Call<ResponseBody>

    @PUT("bind")
    fun bind(@Body body: Any): Call<ResponseBody>

    @PUT("stop")
    fun stop(): Call<ResponseBody>
}