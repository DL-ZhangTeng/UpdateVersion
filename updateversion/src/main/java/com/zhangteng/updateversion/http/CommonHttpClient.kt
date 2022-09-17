package com.zhangteng.updateversion.http

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.zhangteng.updateversion.asynctask.AsyncCheck
import com.zhangteng.updateversion.asynctask.AsyncDownloadForeground
import com.zhangteng.updateversion.callback.DownloadCallback
import com.zhangteng.updateversion.callback.VersionInfoCallback
import com.zhangteng.updateversion.entity.VersionEntity
import java.io.File

/**
 * Created by swing on 2018/5/14.
 */
open class CommonHttpClient(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager
) : HttpClient {
    @SuppressLint("StaticFieldLeak")
    override fun getVersionInfo(versionInfoUrl: String?, versionInfoCallback: VersionInfoCallback) {
        val asyncCheck: AsyncCheck = object : AsyncCheck() {
            override fun doOnPreExecute() {
                versionInfoCallback.onPreExecute(mContext, mFragmentManager, this@CommonHttpClient)
            }

            override fun doDoInBackground(versionEntity: VersionEntity?) {
                versionInfoCallback.doInBackground(versionEntity)
            }

            override fun doOnPostExecute() {
                versionInfoCallback.onPostExecute()
            }
        }
        asyncCheck.execute(versionInfoUrl)
    }

    @SuppressLint("StaticFieldLeak")
    override fun downloadApk(versionEntity: VersionEntity?, downloadCallback: DownloadCallback) {
        val asyncDownloadForeground: AsyncDownloadForeground = object : AsyncDownloadForeground() {
            override fun doOnPreExecute() {
                downloadCallback.onPreExecute(mContext)
            }

            override fun doOnPostExecute(flag: Boolean) {
                downloadCallback.onPostExecute(flag)
            }

            override fun doDoInBackground(total: Long, apkFile: File?) {
                downloadCallback.doInBackground(total, apkFile)
            }

            override fun doOnProgressUpdate(vararg values: Int?) {
                downloadCallback.onProgressUpdate(*values)
            }
        }
        asyncDownloadForeground.execute(versionEntity)
    }
}