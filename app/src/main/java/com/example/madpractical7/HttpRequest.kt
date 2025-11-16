package com.example.madpractical7

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object HttpRequest {
    private const val TAG = "HttpRequest"

    fun makeServiceCall(reqUrl: String, token: String? = null): String? {
        var connection: HttpURLConnection? = null
        return try {
            val url = URL(reqUrl)
            connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                readTimeout = 15000
                connectTimeout = 15000
                doInput = true
                token?.let { setRequestProperty("Authorization", "Bearer $it") }
                setRequestProperty("Content-Type", "application/json")
            }
            val code = connection.responseCode
            val stream: InputStream = if (code in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream ?: connection.inputStream
            }
            stream.bufferedReader().use(BufferedReader::readText)
        } catch (e: Exception) {
            Log.e(TAG, "makeServiceCall error: ${e.message}", e)
            null
        } finally {
            connection?.disconnect()
        }
    }
}
