package com.beran.data.remote.util

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {
    suspend fun <T : Any> safeApiRequest(call: suspend () -> Response<T>): T {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                val respErr = response.errorBody()?.string()
                val message = StringBuilder()
                respErr?.let {
                    try {
                        message.append(JSONObject(it).getString("message"))
                    } catch (_: JSONException) {
                        message.append("Error parsing response body")
                    }
                }
                throw Exception(message.toString())
            }
        } catch (_: IOException) {
            throw Exception("Check you internet connection")
        }
    }
}