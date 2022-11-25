package com.zhangteng.updateversion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import com.zhangteng.updateversion.callback.InstallPermissionCallback
import com.zhangteng.updateversion.config.Constant.REQUEST_INSTALL_PACKAGES_REQUEST_CODE

/**
 * description: 跳转未知应用权限设置页面，需要使用onActivityResult中回调结果
 * author: Swing
 * date: 2022/11/25
 */
class InstallPermissionActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invasionStatusBar(this)
        if (mcallback == null) {
            finish()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startActivityForResult(
                Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES),
                REQUEST_INSTALL_PACKAGES_REQUEST_CODE
            )
        } else {
            mcallback?.success(this)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_INSTALL_PACKAGES_REQUEST_CODE) {
            val haveInstallPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                packageManager.canRequestPackageInstalls()
            } else {
                true
            }
            if (haveInstallPermission) {
                mcallback?.success(this)
            } else {
                mcallback?.failure(this)
            }
        } else {
            mcallback?.failure(this)
        }
        finish()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    companion object {
        private var mcallback: InstallPermissionCallback? = null

        /**
         * Request for permissions.
         */
        fun requestPermission(context: Context, callback: InstallPermissionCallback?) {
            mcallback = callback
            val intent = Intent(context, InstallPermissionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        /**
         * Set the content layout full the StatusBar, but do not hide StatusBar.
         */
        private fun invasionStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                val decorView = window.decorView
                decorView.systemUiVisibility = (decorView.systemUiVisibility
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
            }
        }
    }
}