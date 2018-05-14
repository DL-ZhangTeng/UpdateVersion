package com.zhangteng.updateversionlibrary.http;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import com.zhangteng.updateversionlibrary.asynctask.AsyncCheck;
import com.zhangteng.updateversionlibrary.asynctask.AsyncDownloadForeground;
import com.zhangteng.updateversionlibrary.callback.DownloadCallback;
import com.zhangteng.updateversionlibrary.callback.VersionInfoCallback;
import com.zhangteng.updateversionlibrary.entity.VersionEntity;

import java.io.File;

/**
 * Created by swing on 2018/5/14.
 */
public class CommonHttpClient implements HttpClient {
    private Context mContext;
    private FragmentManager mFragmentManager;

    public CommonHttpClient(Context mContext, FragmentManager mFragmentManager) {
        this.mContext = mContext;
        this.mFragmentManager = mFragmentManager;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getVersionInfo(String versionInfoUrl, final VersionInfoCallback versionInfoCallback) {
        AsyncCheck asyncCheck = new AsyncCheck() {
            @Override
            public void doOnPreExecute() {
                versionInfoCallback.onPreExecute(mContext, mFragmentManager, CommonHttpClient.this);
            }

            @Override
            public void doDoInBackground(VersionEntity versionEntity) {
                versionInfoCallback.doInBackground(versionEntity);
            }

            @Override
            public void doOnPostExecute() {
                versionInfoCallback.onPostExecute();
            }
        };
        asyncCheck.execute(versionInfoUrl);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void downloadApk(VersionEntity versionEntity, final DownloadCallback downloadCallback) {
        AsyncDownloadForeground asyncDownloadForeground = new AsyncDownloadForeground() {
            @Override
            public void doOnPreExecute() {
                downloadCallback.onPreExecute(mContext);
            }

            @Override
            public void doOnPostExecute(Boolean flag) {
                downloadCallback.onPostExecute(flag);
            }

            @Override
            public void doDoInBackground(long total, File apkFile) {
                downloadCallback.doInBackground(total, apkFile);
            }

            @Override
            public void doOnProgressUpdate(Integer... values) {
                downloadCallback.onProgressUpdate(values);
            }
        };
        asyncDownloadForeground.execute(versionEntity);
    }
}
