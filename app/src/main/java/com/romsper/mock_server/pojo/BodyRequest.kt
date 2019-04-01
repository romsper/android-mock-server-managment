package com.romsper.mock_server.pojo
import com.google.gson.annotations.SerializedName


open class BodyRequest(
    @SerializedName("keepAlive")
    var keepAlive: Boolean?,
    @SerializedName("method")
    var method: String?,
    @SerializedName("path")
    var path: String?,
    @SerializedName("secure")
    var secure: Boolean?
)

class BodyTool: BodyRequest(
    method = null,
    path = null,
    keepAlive = null,
    secure = null
)