package com.romsper.mock_server.asyncTask

import android.os.AsyncTask
import android.util.Log
import com.google.gson.GsonBuilder
import com.romsper.mock_server.`interface`.IResponseResult
import com.romsper.mock_server.api.MockServerApi
import com.romsper.mock_server.network.Retrofit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetToolResultAsyncTask(val resultListener: IResponseResult) :
    AsyncTask<GetToolResultAsyncTask.Params, Void, String>() {
    lateinit var gson: GsonBuilder

    override fun doInBackground(vararg params: Params?): String? {
        val spec = params[0]!!
        val request = Retrofit.getInstance(spec.host)
            .retrieve(body = "", format = "", type = spec.type)

        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when (response.code()) {
                    in 200..299 -> {
                        resultListener.onFailure(response.body()!!.string())
                        resultListener.onSuccess(response.code())
                        response.body()!!.string()
                    }
                    in 400..499 -> resultListener.onError(response.code())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                resultListener.onFailure(t.localizedMessage)
            }
        })
        return null
    }

    data class Params(
        val host: String,
        val tool: String,
        val format: String,
        val type: String,
        val path: String,
        val method: String
    )
}