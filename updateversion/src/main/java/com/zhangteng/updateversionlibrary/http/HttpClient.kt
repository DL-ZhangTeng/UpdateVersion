package com.zhangteng.updateversionlibrary.http;

import com.zhangteng.updateversionlibrary.callback.DownloadCallback;
import com.zhangteng.updateversionlibrary.callback.VersionInfoCallback;
import com.zhangteng.updateversionlibrary.entity.VersionEntity;

/**
 * 联网客户端
 * Created by swing on 2018/5/11.
 */
public interface HttpClient {

    /**
     * 执行获取版本信息请求
     *
     * @param versionInfoUrl
     * @param versionInfoCallback 做非空判断，为空则new，在网络请求中调用回调的各个异步任务方法
     */
    void getVersionInfo(String versionInfoUrl, VersionInfoCallback versionInfoCallback);

    /**
     * 执行获取下载请求
     *
     * @param versionEntity
     * @param downloadCallback 做非空判断，为空则new，在网络请求中调用回调的各个异步任务方法
     */
    void downloadApk(VersionEntity versionEntity, DownloadCallback downloadCallback);
}
