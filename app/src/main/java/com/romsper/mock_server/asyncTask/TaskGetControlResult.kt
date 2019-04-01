package com.romsper.mock_server.asyncTask

import android.os.AsyncTask
import com.romsper.mock_server.`interface`.IResponseResult
import com.romsper.mock_server.network.Retrofit
import com.romsper.mock_server.pojo.BodyTool
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception


class TaskGetControlResult(private val resultListener: IResponseResult) :
    AsyncTask<TaskGetControlResult.Params, Void, Response<ResponseBody>?>() {

    override fun doInBackground(vararg params: Params?): Response<ResponseBody>? {
        val spec = params[0]!!
        val retrofit = Retrofit.getInstance(spec.host)

        when (spec.control) {
            "CLEAR" -> {
                val requestBody = BodyTool()
                    .apply {
                        method = spec.method
                        path = spec.path
                    }

                val request = retrofit.clear(body = requestBody)

                try {
                    return request.execute()
                } catch (e: Exception) {
                    resultListener.onFailure(e.localizedMessage)
                }
            }
            "RESET" -> {
                val request = retrofit.reset()

                try {
                    return request.execute()
                } catch (e: Exception) {
                    resultListener.onFailure(e.localizedMessage)
                }
            }
            "RETRIEVE" -> {
                val requestBody = BodyTool()
                    .apply {
                        method = spec.method
                        path = spec.path
                    }
                val request = retrofit.retrieve(body = requestBody, format = null, type = spec.type)

                try {
                    return request.execute()
                } catch (e: Exception) {
                    resultListener.onFailure(e.localizedMessage)
                }
            }
            "STATUS" -> {
                val request = retrofit.status()

                try {
                    return request.execute()
                } catch (e: Exception) {
                    resultListener.onFailure(e.localizedMessage)
                }
            }
            "STOP" -> {
                val request = retrofit.stop()

                try {
                    return request.execute()
                } catch (e: Exception) {
                    resultListener.onFailure(e.localizedMessage)
                }
            }
        }
        return null
    }

    override fun onPostExecute(response: Response<ResponseBody>?) {
        super.onPostExecute(response)

        if (response == null) return
        when (response.code()) {
            in 200..299 -> {
                resultListener.onSuccess(response.code(), response.body()!!.string())
            }
            in 400..499 -> resultListener.onError(response.code())
        }
    }

    data class Params(
        val host: String,
        val control: String,
        val format: String,
        val type: String,
        val path: String,
        val method: String
    )
}