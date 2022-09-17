package com.zhangteng.updateversion.config

import android.os.Environment

/**
 * @author swing 2018/5/14
 */
object Constant {
    var PATH: String = Environment
        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
    const val SUFFIX = ".apk"
    const val APK_PATH = "APK_PATH"
    const val APP_NAME = "APP_NAME"
    var cache = HashMap<String?, String?>()

    //通知进度信号
    const val UPDATE_NOTIFICATION_PROGRESS = 0x1

    //安装信号
    const val COMPLETE_DOWNLOAD_APK = 0x2

    //通知id
    const val DOWNLOAD_NOTIFICATION_ID = 0x3

    //渠道参数
    const val NOTIFICATION_CHANNEL_ID = "UpdateVersion"
    const val NOTIFICATION_CHANNEL_NAME = "版本更新"
    const val NOTIFICATION_CHANNEL_DESCRIPTION = "应用内版本更新"

    //进度条最大值（影响精度）
    const val PROGRESS_MAX = 1000
}