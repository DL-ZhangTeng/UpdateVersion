package com.zhangteng.updateversion.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.zhangteng.updateversion.R
import com.zhangteng.updateversion.UpdateVersion

/**
 * description: Android8.0的变化是，未知应用安装权限的开关被除掉，取而代之的是未知来源应用的管理列表，需要在里面打开每个应用的未知来源的安装权限
 * author: Swing
 * date: 2022/11/25
 */
class InstallPermissionDialog(context: Context?, theme: Int) : AlertDialog(context, theme) {
    private var submitTxt: TextView? = null
    private var cancelTxt: TextView? = null
    private var onSubmitListener: View.OnClickListener? = null
    private var onCancelListener: View.OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_permission_dialog)
        submitTxt = findViewById(R.id.submit)
        cancelTxt = findViewById(R.id.cancel)
        if (UpdateVersion.themeColor != R.color.version_theme_color) {
            @Suppress("DEPRECATION")
            submitTxt?.setTextColor(context.resources.getColor(UpdateVersion.themeColor))
        }
        submitTxt?.setOnClickListener {
            onSubmitListener?.onClick(it)
            dismiss()
        }
        cancelTxt?.setOnClickListener {
            onCancelListener?.onClick(it)
            dismiss()
        }
    }

    fun setCancelListener(onSubmitListener: View.OnClickListener?): InstallPermissionDialog {
        this.onSubmitListener = onSubmitListener
        return this
    }

    fun setSubmitListener(onCancelListener: View.OnClickListener?): InstallPermissionDialog {
        this.onSubmitListener = onCancelListener
        return this
    }
}