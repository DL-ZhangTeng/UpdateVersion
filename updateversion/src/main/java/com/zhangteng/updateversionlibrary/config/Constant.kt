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
}