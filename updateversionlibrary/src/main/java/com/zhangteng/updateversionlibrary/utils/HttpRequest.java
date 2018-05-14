package com.zhangteng.updateversionlibrary.utils;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * @author swing
 */
public class HttpRequest {

    public static InputStream get(String url) {
        try {
            URL urlPath = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlPath.openConnection();
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
