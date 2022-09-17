package com.zhangteng.updateversion.callback

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.zhangteng.updateversion.R
import com.zhangteng.updateversion.UpdateVersion
import com.zhangteng.updateversion.config.Constant
import com.zhangteng.updateversion.dialog.CommonProgressDialog
import java.io.File

/**
 * 下载任务进度监听
 *
 * @author swing 2018/5/11
 */
class DownloadCallback {
    private var mContext: Context? = null
    private var total: Long = 0
    private var apkFile: File? = null
    private var progressDialog: CommonProgressDialog? = null
    private var notificationManager: NotificationManager? = null
    private var ntfBuilder: NotificationCompat.Builder? = null

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                UPDATE_NOTIFICATION_PROGRESS -> showDownloadNotificationUI(msg.arg1, msg.arg2)
                COMPLETE_DOWNLOAD_APK -> if (UpdateVersion.Companion.isAutoInstall()) {
                    installApk(apkFile)
                } else {
                    ntfBuilder = NotificationCompat.Builder(mContext!!)
                    ntfBuilder!!.setSmallIcon(mContext!!.applicationInfo.icon)
                        .setContentTitle(Constant.cache[Constant.APP_NAME])
                        .setContentText(
                            if (mContext == null) "下载完成，点击安装" else mContext!!.getString(
                                R.string.notification_content_finish
                            )
                        )
                        .setTicker(if (mContext == null) "任务下载完成" else mContext!!.getString(R.string.notification_ticker_finish))
                    val intent = Intent(Intent.ACTION_VIEW)
                    //判断是否是AndroidN以及更高的版本
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.flags =
                            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                        val uri = UpdateVersion.provider?.let {
                            FileProvider.getUriForFile(
                                mContext!!,
                                it,
                                apkFile!!
                            )
                        }
                        intent.setDataAndType(uri, "application/vnd.android.package-archive")
                    } else {
                        intent.setDataAndType(
                            Uri.fromFile(apkFile),
                            "application/vnd.android.package-archive"
                        )
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    val pendingIntent = PendingIntent.getActivity(
                        mContext, 0, intent, 0
                    )
                    ntfBuilder!!.setContentIntent(pendingIntent)
                    if (notificationManager == null) {
                        notificationManager =
                            mContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    }
                    notificationManager!!.notify(
                        DOWNLOAD_NOTIFICATION_ID,
                        ntfBuilder!!.build()
                    )
                }
                else -> {}
            }
        }
    }

    /**
     * 开始下载前的准备工作
     */
    fun onPreExecute(context: Context?) {
        mContext = context
        if (UpdateVersion.isProgressDialogShow()) {
            progressDialog = CommonProgressDialog(context, R.style.Translucent_Dialog)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setMessage(if (mContext == null) "正在下载更新" else mContext!!.getString(R.string.progress_message))
            if (!UpdateVersion.isNotificationShow) {
                progressDialog!!.show()
            }
        }
    }

    /**
     * 下载完成后发送安装请求
     */
    fun onPostExecute(flag: Boolean) {
        if (flag) {
            //下载成功执行安装步骤
            if (!UpdateVersion.isNotificationShow) {
                handler.obtainMessage(COMPLETE_DOWNLOAD_APK).sendToTarget()
            }
        } else {
            Log.e("Error", "下载失败。")
            Toast.makeText(
                mContext,
                if (mContext == null) "下载失败，请到应用商城或官网下载" else mContext!!.getString(R.string.download_failure),
                Toast.LENGTH_LONG
            ).show()
        }
        if (UpdateVersion.isProgressDialogShow()) {
            progressDialog!!.dismiss()
        }
    }

    /**
     * 从背景任务中获取apk大小及下载完成后的文件对象
     */
    fun doInBackground(total: Long, apkFile: File?) {
        this.total = total
        this.apkFile = apkFile
    }

    /**
     * 下载进度监听
     */
    fun onProgressUpdate(vararg values: Int?) {
        if (UpdateVersion.isProgressDialogShow()) {
            progressDialog?.max = total.toInt()
            values[0]?.let {
                progressDialog!!.setProgress(it)
            }
        }
        if (UpdateVersion.isNotificationShow) {
            values[0]?.let {
                handler.obtainMessage(UPDATE_NOTIFICATION_PROGRESS, it, total.toInt())
                    .sendToTarget()
            }
        }
    }

    /**
     * 通知栏弹出下载提示进度
     *
     * @param progress
     */
    private fun showDownloadNotificationUI(progress: Int, total: Int) {
        if (mContext != null) {
            val pro = progress * 100 / total
            val contentText = "$pro%"
            val contentIntent = PendingIntent.getActivity(
                mContext,
                0, Intent(), PendingIntent.FLAG_CANCEL_CURRENT
            )
            if (notificationManager == null) {
                notificationManager = mContext!!
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            if (ntfBuilder == null) {
                ntfBuilder = NotificationCompat.Builder(mContext!!)
                    .setSmallIcon(mContext!!.applicationInfo.icon)
                    .setTicker(if (mContext == null) "开始下载…" else mContext!!.getString(R.string.notification_ticker_start))
                    .setContentTitle(if (mContext == null) "更新" else mContext!!.getString(R.string.notification_ticker_start))
                    .setContentIntent(contentIntent)
            }
            ntfBuilder!!.setContentText(contentText)
            ntfBuilder!!.setProgress(total, progress, false)
            notificationManager!!.notify(
                DOWNLOAD_NOTIFICATION_ID,
                ntfBuilder!!.build()
            )
            if (total == progress) {
                ntfBuilder!!.setProgress(0, 0, true)
                notificationManager!!.notify(
                    DOWNLOAD_NOTIFICATION_ID,
                    ntfBuilder!!.build()
                )
                notificationManager!!.cancel(DOWNLOAD_NOTIFICATION_ID)
                handler.obtainMessage(COMPLETE_DOWNLOAD_APK).sendToTarget()
            }
        }
    }

    /**
     * 安装apk
     *
     * @param apkFile 安装包文件
     */
    private fun installApk(apkFile: File?) {
        if (mContext != null) {
            val intent = Intent(Intent.ACTION_VIEW)
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.flags =
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                val uri = UpdateVersion.provider?.let {
                    FileProvider.getUriForFile(
                        mContext!!,
                        it,
                        apkFile!!
                    )
                }
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(
                    Uri.fromFile(apkFile),
                    "application/vnd.android.package-archive"
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            mContext!!.startActivity(intent)
            if (notificationManager != null) {
                notificationManager!!.cancel(DOWNLOAD_NOTIFICATION_ID)
            }
        } else {
            Log.e("NullPointerException", "The context must not be null.")
        }
        this.apkFile = null
    }

    companion object {
        private const val UPDATE_NOTIFICATION_PROGRESS = 0x1
        private const val COMPLETE_DOWNLOAD_APK = 0x2
        private const val DOWNLOAD_NOTIFICATION_ID = 0x3
    }
}