package com.zhangteng.updateversion.callback

import android.app.Activity

interface InstallPermissionCallback {
    fun success(permissionActivity: Activity?)

    fun failure(permissionActivity: Activity?)
}