package com.zhangteng.updateversion.http

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.zhangteng.updateversion.asynctask.AsyncCheck
import com.zhangteng.updateversion.asynctask.AsyncDownloadForeground
import com.zhangteng.updateversion.callback.DownloadCallback
import com.zhangteng.updateversion.callback.VersionInfoCallback
import com.zhangteng.updateversion.entity.VersionEntity
import kotlinx.coroutines.*
import java.io.File

/**
 * Created by swing on 2018/5/14.
 */
open class CommonHttpClient(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager
) : HttpClient {
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("StaticFieldLeak")
    override fun getVersionInfo(versionInfoUrl: String?, versionInfoCallback: VersionInfoCallback) {
        val asyncCheck: AsyncCheck = object : AsyncCheck() {
            override fun doOnPreExecute() {
                versionInfoCallback.onPreExecute(
                    mContext,
                    mFragmentManager,
                    this@CommonHttpClient
                )
            }

            override fun doDoInBackground(result: VersionEntity?) {
                versionInfoCallback.doInBackground(result)
            }

            override fun doOnPostExecute(result: VersionEntity?) {
                versionInfoCallback.onPostExecute()
            }
        }
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                asyncCheck.onPreExecute()
                withContext(Dispatchers.IO) {
                    val versionEntity = asyncCheck.doInBackground(versionInfoUrl)
                    withContext(Dispatchers.Main) {
                        asyncCheck.onPostExecute(versionEntity)
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("StaticFieldLeak")
    override fun downloadApk(versionEntity: VersionEntity?, downloadCallback: DownloadCallback) {
        val asyncDownloadForeground: AsyncDownloadForeground =
            object : AsyncDownloadForeground() {
                override fun doOnPreExecute() {
                    downloadCallback.onPreExecute(mContext)
                }

                override fun doOnPostExecute(flag: Boolean?) {
                    downloadCallback.onPostExecute(flag ?: false)
                }

                override fun doDoInBackground(total: Long, apkFile: File?) {
                    downloadCallback.doInBackground(total, apkFile)
                }

                override fun doOnProgressUpdate(vararg values: Int?) {
                    downloadCallback.onProgressUpdate(*values)
                }
            }
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                asyncDownloadForeground.onPreExecute()
                withContext(Dispatchers.IO) {
                    val result = asyncDownloadForeground.doInBackground(versionEntity)
                    withContext(Dispatchers.Main) {
                        asyncDownloadForeground.onPostExecute(result)
                    }
                }
            }
        }
    }
}