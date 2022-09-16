package com.zhangteng.updateversion.callback

import android.annotation.SuppressLint
import android.app.DialogFragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.zhangteng.updateversion.R
import com.zhangteng.updateversion.UpdateVersion
import com.zhangteng.updateversion.dialog.UpdateDialogFragment
import com.zhangteng.updateversion.entity.VersionEntity
import com.zhangteng.updateversion.http.HttpClient
import com.zhangteng.utils.NetType
import com.zhangteng.utils.getConnectedType
import java.io.InputStream

/**
 * @author swing 2018/5/14
 */
open class VersionInfoCallback {
    private var versionEntity: VersionEntity? = null
    private var mFragmentManager: FragmentManager? = null
    private var httpClient: HttpClient? = null

    /**
     * 开始获取版本信息前的准备工作
     */
    fun onPreExecute(
        context: Context?,
        fragmentManager: FragmentManager?,
        httpClient: HttpClient?
    ) {
        mContext = context
        mFragmentManager = fragmentManager
        this.httpClient = httpClient
    }

    /**
     * 从背景任务中获取版本信息
     */
    fun doInBackground(versionEntity: VersionEntity?) {
        this.versionEntity = versionEntity
    }

    /**
     * 请求完成后进行下载请求
     */
    fun onPostExecute() {
        if (mContext != null && versionEntity != null) {
            Log.i(
                "auto update",
                "versionEntity versioncode: " + versionEntity!!.versionNo + " package versioncode: " + packageInfo!!.versionCode
            )
            if (versionEntity!!.versionCode > packageInfo!!.versionCode) {
                if (versionEntity!!.forceUpdate != 0) {
                    UpdateVersion.setIsAutoInstall(true)
                    UpdateVersion.setIsProgressDialogShow(true)
                    val type = mContext.getConnectedType()
                    if (type != NetType.Wifi && UpdateVersion.isNetCustomDialogShow) {
                        showUpdateUICustom(versionEntity!!)
                    } else {
                        httpClient!!.downloadApk(versionEntity, DownloadCallback())
                    }
                } else {
                    if (UpdateVersion.isUpdateDialogShow) {
                        showUpdateUICustom(versionEntity!!)
                    } else {
                        val type = mContext.getConnectedType()
                        if (type != NetType.Wifi && UpdateVersion.isNetCustomDialogShow) {
                            showNetCustomDialog(versionEntity!!)
                        } else {
                            if (!UpdateVersion.isUpdateDownloadWithBrowser) {
                                httpClient!!.downloadApk(versionEntity, DownloadCallback())
                            } else {
                                val i =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(versionEntity!!.url))
                                mContext!!.startActivity(i)
                            }
                        }
                    }
                }
            } else {
                if (UpdateVersion.isHintVersion) {
                    Toast.makeText(
                        mContext,
                        if (mContext == null) "当前已是最新版" else mContext!!.getString(R.string.version_hint),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            if (UpdateVersion.isHintVersion) {
                Toast.makeText(
                    mContext,
                    if (mContext == null) "当前已是最新版" else mContext!!.getString(R.string.version_hint),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * 获取当前app版本
     */
    private val packageInfo: PackageInfo?
        private get() {
            var pinfo: PackageInfo? = null
            if (mContext != null) {
                try {
                    pinfo = mContext!!.packageManager.getPackageInfo(
                        mContext!!.packageName, 0
                    )
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
            }
            return pinfo
        }

    /**
     * 更新提示
     */
    @SuppressLint("WrongConstant")
    private fun showUpdateUICustom(versionEntity: VersionEntity) {
        val dialogFragment = UpdateDialogFragment()
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        dialogFragment.setTitle(
            String.format(
                if (mContext == null) "发现新版本%s" else mContext!!.getString(R.string.version_title),
                """
     
     ${versionEntity.versionNo}
     """.trimIndent()
            )
        )
        dialogFragment.setContentTitleText(
            if (TextUtils.isEmpty(versionEntity.title)) if (mContext == null) "更新的内容" else mContext!!.getString(
                R.string.version_content_title
            ) else versionEntity.title
        )
        dialogFragment.setContentText(
            String.format(
                if (mContext == null) "%s" else mContext!!.getString(
                    R.string.version_content
                ), versionEntity.updateDesc
            )
        )
        dialogFragment.setNegativeBtn(
            if (mContext == null) "暂不" else mContext!!.getString(R.string.version_cancel),
            null
        )
        dialogFragment.setPositiveBtn(if (mContext == null) "立即更新" else mContext!!.getString(R.string.version_confirm),
            object : UpdateDialogFragment.OnClickListener {
                override fun onClick() {
                    val type = mContext.getConnectedType()
                    if (type != NetType.Wifi && UpdateVersion.isNetCustomDialogShow) {
                        showNetCustomDialog(versionEntity)
                    } else {
                        if (!UpdateVersion.isUpdateDownloadWithBrowser) {
                            httpClient!!.downloadApk(versionEntity, DownloadCallback())
                        } else {
                            val i = Intent(Intent.ACTION_VIEW, Uri.parse(versionEntity.url))
                            mContext!!.startActivity(i)
                        }
                    }
                }
            })
        try {
            dialogFragment.show(mFragmentManager!!, "")
        } catch (e: IllegalStateException) {
            Log.e("UpdateDialogFragment", e.message!!)
        }
    }

    /**
     * 手机网络dialog
     */
    @SuppressLint("WrongConstant")
    private fun showNetCustomDialog(versionEntity: VersionEntity) {
        val dialogFragment = UpdateDialogFragment()
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        dialogFragment.setContentText(
            if (mContext == null) "当前在无WIFI的情况下下载，请确定是否使用流量继续下载。" else mContext!!.getString(
                R.string.no_wifi_hint
            )
        )
        dialogFragment.setNetHint(true)
        dialogFragment.setNegativeBtn(
            if (mContext == null) "取消" else mContext!!.getString(R.string.no_wifi_hint_cancel),
            null
        )
        dialogFragment.setPositiveBtn(if (mContext == null) "继续下载" else mContext!!.getString(R.string.no_wifi_hint_confirm),
            object : UpdateDialogFragment.OnClickListener {
                override fun onClick() {
                    if (!UpdateVersion.isUpdateDownloadWithBrowser) {
                        httpClient!!.downloadApk(versionEntity, DownloadCallback())
                    } else {
                        val i = Intent(Intent.ACTION_VIEW, Uri.parse(versionEntity.url))
                        mContext!!.startActivity(i)
                    }
                }
            })
        try {
            dialogFragment.show(mFragmentManager!!, "")
        } catch (e: IllegalStateException) {
            Log.e("UpdateDialogFragment", e.message!!)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null
        fun nativeAssertGet(URL: String?): InputStream? {
            var inputStream: InputStream? = null
            try {
                inputStream = mContext!!.assets.open(URL!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return inputStream
        }
    }
}