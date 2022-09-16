package com.zhangteng.updateversionlibrary.asynctask;

import android.os.AsyncTask;

import com.zhangteng.updateversionlibrary.UpdateVersion;
import com.zhangteng.updateversionlibrary.config.Constant;
import com.zhangteng.updateversionlibrary.entity.VersionEntity;
import com.zhangteng.utils.SSLUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by swing on 2018/5/14.
 */
public abstract class AsyncDownloadForeground extends AsyncTask<VersionEntity, Integer, Boolean> {
    private long total;
    private File apkFile = null;

    /**
     * 开始下载前的准备工作
     */
    public abstract void doOnPreExecute();

    /**
     * 下载完成后发送安装请求
     */
    public abstract void doOnPostExecute(Boolean flag);

    /**
     * 从背景任务中获取apk大小及下载完成后的文件对象
     */
    public abstract void doDoInBackground(long total, File apkFile);

    /**
     * 下载进度监听
     */
    public abstract void doOnProgressUpdate(Integer... values);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        doOnPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        doOnPostExecute(aBoolean);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        doOnProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(VersionEntity... params) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(params[0].getUrl());
            urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) urlConnection).setSSLSocketFactory(UpdateVersion.getSslParams().getSSLSocketFactory());
                ((HttpsURLConnection) urlConnection).setHostnameVerifier(SSLUtils.INSTANCE.getUnSafeHostnameVerifier());
            }
            // 2.2版本以上HttpURLConnection跟服务交互采用了"gzip"压缩，添加这行代码避免total = -1
            urlConnection.setRequestProperty("Accept-Encoding", "identity");
            //设置超时间为3秒
            urlConnection.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            } else {
                //得到输入流
                InputStream inputStream = urlConnection.getInputStream();

                total = urlConnection.getContentLength();
                String apkName = params[0].getName()
                        + params[0].getVersionNo() + Constant.SUFFIX;
                Constant.cache.put(Constant.APP_NAME, params[0].getName());
                Constant.cache.put(Constant.APK_PATH,
                        Constant.PATH + File.separator + params[0].getName()
                                + File.separator + apkName);

                File savePath = new File(Constant.PATH + File.separator
                        + params[0].getName());

                if (!savePath.exists()) {
                    savePath.mkdirs();
                }
                //6.0以上系统需要静态动态权限不然无法创建文件
                apkFile = new File(savePath, apkName);
                if (apkFile.exists()) {
                    apkFile.delete();
                    //return true;
                }
                doDoInBackground(total, apkFile);
                FileOutputStream fos = new FileOutputStream(apkFile);
                // FileOutputStream fos = mContext.openFileOutput(apkFile.getAbsolutePath().toString(),mContext.MODE_PRIVATE);
                byte[] buf = new byte[1024];
                int count = 0;
                int length = -1;
                while ((length = inputStream.read(buf)) != -1) {
                    fos.write(buf, 0, length);
                    count += length;
                    publishProgress(count);
                }
                inputStream.close();
                fos.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            doDoInBackground(total, apkFile);
            return false;
        }
        return true;
    }
}
