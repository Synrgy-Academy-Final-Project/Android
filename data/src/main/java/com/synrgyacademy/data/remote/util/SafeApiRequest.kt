package com.synrgyacademy.data.remote.util

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {
    suspend fun <T : Any> safeApiRequest(call: suspend () -> Response<T>): T {
        try {
            val response = call.invoke()
            when {
                response.isSuccessful -> {
                    return response.body()!!
                }

                response.code() == 401 -> {
                    throw UnauthorizedException("Tidak Diizinkan: Autentikasi pengguna gagal")
                }

                response.code() == 403 -> {
                    throw ForbiddenException("Dilarang: Akses ditolak")
                }

                response.code() == 404 -> {
                    throw NotFoundException("Tidak Ditemukan: Sumber daya tidak ditemukan")
                }

                response.code() == 500 -> {
                    throw InternalServerException("Kesalahan Server Internal")
                }

                else -> {
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
            }
        } catch (_: IOException) {
            throw Exception("Periksa koneksi internet anda")
        }
    }
}