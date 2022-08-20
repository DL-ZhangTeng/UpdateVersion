package com.zhangteng.updateversionlibrary.utils;

import android.util.Log;

import com.zhangteng.updateversionlibrary.UpdateVersion;
import com.zhangteng.utils.SSLUtils;
import com.zhangteng.utils.URLUtilsKt;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author swing
 */
public class HttpRequest {

    public static InputStream get(String url) {
        try {
            URL urlPath = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlPath.openConnection();
            if (URLUtilsKt.isHttpsUrl(url) && httpURLConnection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(UpdateVersion.getSslParams().getSSLSocketFactory());
                ((HttpsURLConnection) httpURLConnection).setHostnameVerifier(SSLUtils.INSTANCE.getUnSafeHostnameVerifier());
            }
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setReadTimeout(3000);

            httpURLConnection.connect();
            InputStream inputStream = null;
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            return inputStream;
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TimeOut", "the connection is timeout, maybe the server was closed.");
            return null;
        }
    }
}
