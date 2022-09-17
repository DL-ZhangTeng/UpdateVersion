package com.zhangteng.updateversion.http

import com.zhangteng.updateversion.callback.DownloadCallback
import com.zhangteng.updateversion.callback.VersionInfoCallback
import com.zhangteng.updateversion.entity.VersionEntity

/**
 * 联网客户端
 * Created by swing on 2018/5/11.
 */
interface HttpClient {
    /**
     * 执行获取版本信息请求
     *
     * @param versionInfoUrl
     * @param versionInfoCallback 做非空判断，为空则new，在网络请求中调用回调的各个异步任务方法
     */
    fun getVersionInfo(versionInfoUrl: String?, versionInfoCallback: VersionInfoCallback)

    /**
     * 执行获取下载请求
     *
     * @param versionEntity
     * @param downloadCallback 做非空判断，为空则new，在网络请求中调用回调的各个异步任务方法
     */
    fun downloadApk(versionEntity: VersionEntity?, downloadCallback: DownloadCallback)
}