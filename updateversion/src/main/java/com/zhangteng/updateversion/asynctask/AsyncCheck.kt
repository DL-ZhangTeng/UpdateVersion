package com.zhangteng.updateversion.asynctask

import android.util.Log
import com.zhangteng.updateversion.UpdateVersion
import com.zhangteng.updateversion.callback.VersionInfoCallback
import com.zhangteng.updateversion.entity.VersionEntity
import com.zhangteng.updateversion.utils.HttpRequest
import com.zhangteng.updateversion.utils.JSONHandler
import com.zhangteng.utils.isNetworkUrl

/**
 * Created by swing on 2018/5/14.
 */
abstract class AsyncCheck : AsyncTask<String, Int, VersionEntity>() {
    /**
     * 准备执行
     */
    abstract fun doOnPreExecute()

    /**
     * 获取版本信息后执行
     *
     * @param result 版本信息
     */
    abstract fun doDoInBackground(result: VersionEntity?)

    /**
     * 任务完成
     */
    abstract fun doOnPostExecute(result: VersionEntity?)

    override fun onPreExecute() {
        doOnPreExecute()
    }

    override suspend fun doInBackground(vararg params: String?): VersionEntity? {
        var versionEntity: VersionEntity? = null
        if (params.isEmpty()) {
            Log.e(
                "NullPointerException",
                " Url parameter must not be null."
            )
            return null
        }
        val url = params[0]
        if (!url.isNetworkUrl()) {
            return null
        }
        try {
            if (UpdateVersion.isUpdateTest) {
                versionEntity =
                    JSONHandler.toVersionEntity(VersionInfoCallback.nativeAssertGet("versionInfo.json"))
            } else {
                if (HttpRequest[url] != null) {
                    versionEntity = JSONHandler.toVersionEntity(HttpRequest[url])
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        doDoInBackground(versionEntity)
        return versionEntity
    }

    override fun onPostExecute(result: VersionEntity?) {
        doOnPostExecute(result)
    }
}