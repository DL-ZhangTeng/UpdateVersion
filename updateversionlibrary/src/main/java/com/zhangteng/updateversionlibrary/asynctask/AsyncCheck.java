package com.zhangteng.updateversionlibrary.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.zhangteng.updateversionlibrary.UpdateVersion;
import com.zhangteng.updateversionlibrary.callback.VersionInfoCallback;
import com.zhangteng.updateversionlibrary.entity.VersionEntity;
import com.zhangteng.updateversionlibrary.utils.HttpRequest;
import com.zhangteng.updateversionlibrary.utils.JSONHandler;
import com.zhangteng.updateversionlibrary.utils.URLUtils;

/**
 * Created by swing on 2018/5/14.
 */
public abstract class AsyncCheck extends AsyncTask<String, Integer, VersionEntity> {

    /**
     * 准备执行
     */
    public abstract void doOnPreExecute();

    /**
     * 获取版本信息后执行
     *
     * @param versionEntity 版本信息
     */
    public abstract void doDoInBackground(VersionEntity versionEntity);

    /**
     * 任务完成
     */
    public abstract void doOnPostExecute();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        doOnPreExecute();
    }

    @Override
    protected VersionEntity doInBackground(String... params) {
        VersionEntity versionEntity = null;
        if (params.length == 0) {
            Log.e("NullPointerException",
                    " Url parameter must not be null.");
            return null;
        }
        String url = params[0];
        if (!URLUtils.isNetworkUrl(url)) {
            return null;
        }
        try {
            if (UpdateVersion.isUpdateTest()) {
                versionEntity = JSONHandler.toVersionEntity(VersionInfoCallback.nativeAssertGet("versionInfo.json"));
            } else {
                if (HttpRequest.get(url) != null) {
                    versionEntity = JSONHandler.toVersionEntity(HttpRequest.get(url));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        doDoInBackground(versionEntity);
        return versionEntity;
    }

    @Override
    protected void onPostExecute(VersionEntity versionEntity) {
        super.onPostExecute(versionEntity);
        doOnPostExecute();
    }
}
