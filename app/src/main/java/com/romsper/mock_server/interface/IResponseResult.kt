package com.romsper.mock_server.`interface`

interface IResponseResult {

    fun onSuccess(code: Int) {}

    fun onError(code: Int) {}

    fun onFailure(localizedMessage: String) {}
}