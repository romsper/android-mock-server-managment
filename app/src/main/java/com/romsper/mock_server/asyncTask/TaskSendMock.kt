package com.romsper.mock_server.asyncTask

import android.os.AsyncTask
import com.romsper.mock_server.`interface`.IResponseResult
import com.romsper.mock_server.network.Retrofit
import com.romsper.mock_server.pojo.CreateMock
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskSendMock(val resultListener: IResponseResult) : AsyncTask<TaskSendMock.Params, Void, Void>() {

    override fun doInBackground(vararg params: Params): Void? {
        val spec = params[0]
        val mock = Retrofit.getInstance(spec.host)
            .createMock(body = spec.mock)

        mock.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when (response.code()) {
                    in 200..299 -> resultListener.onSuccess(response.code(), response.body()!!.string())
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
        val mock: CreateMock,
        val host: String
    )
}