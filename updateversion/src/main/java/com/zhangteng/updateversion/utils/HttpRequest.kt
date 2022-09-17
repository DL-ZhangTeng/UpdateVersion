package com.zhangteng.updateversion.utils

import android.util.Log
import com.zhangteng.updateversion.UpdateVersion
import com.zhangteng.utils.SSLUtils.UnSafeHostnameVerifier
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * @author swing
 */
object HttpRequest {
    operator fun get(url: String?): InputStream? {
        return try {
            val urlPath = URL(url)
            val httpURLConnection = urlPath.openConnection() as HttpURLConnection
            if (httpURLConnection is HttpsURLConnection) {
                httpURLConnection.sslSocketFactory =
                    UpdateVersion.sslParams?.sSLSocketFactory
                httpURLConnection.hostnameVerifier = UnSafeHostnameVerifier
            }
            httpURLConnection.connectTimeout = 3000
            httpURLConnection.readTimeout = 3000
            httpURLConnection.connect()
            var inputStream: InputStream? = null
            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.inputStream
            }
            inputStream
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TimeOut", "the connection is timeout, maybe the server was closed.")
            null
        }
    }
}