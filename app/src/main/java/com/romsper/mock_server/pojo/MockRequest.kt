package com.romsper.mock_server.pojo
import com.google.gson.annotations.SerializedName


open class MockRequest(
    @SerializedName("httpRequest")
    var httpRequest: HttpRequest,
    @SerializedName("httpResponse")
    var httpResponse: HttpResponse,
    @SerializedName("times")
    var times: Times?
)

data class Times(
    @SerializedName("unlimited")
    var unlimited: Boolean?
)

data class HttpRequest(
    @SerializedName("headers")
    var headers: Headers? = null,
    @SerializedName("secure")
    var secure: Boolean? = null,
    @SerializedName("method")
    var method: String? = null,
    @SerializedName("path")
    var path: String?
)

data class Headers(
    @SerializedName("x-api-key")
    var xApiKey: List<String?>?
)

data class HttpResponse(
    @SerializedName("body")
    var body: String?
)

class CreateMock: MockRequest(
    httpRequest = HttpRequest(
        method = "GET",
        path = "",
        secure = false,
        headers = null
    ),
    httpResponse = HttpResponse(
        body = ""
    ),
    times = Times(
        unlimited = true
    )
)