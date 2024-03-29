package com.zhangteng.updateversion

import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.zhangteng.updateversion.callback.VersionInfoCallback
import com.zhangteng.updateversion.http.HttpClient
import com.zhangteng.utils.SSLUtils
import com.zhangteng.utils.SSLUtils.sslSocketFactory

/**
 * Created by swing on 2018/5/11.
 */
class UpdateVersion(private var builder: Builder) : VersionInfoCallback() {
    fun setBuilder(builder: Builder) {
        this.builder = builder
        initParams(builder)
    }

    private fun initParams(builder: Builder) {
        isAutoInstall = builder.isAutoInstall
        isProgressDialogShow = builder.isProgressDialogShow
        isNotificationShow = builder.isNotificationShow

        isUpdateDialogShow = builder.isUpdateDialogShow
        isNetCustomDialogShow = builder.isNetCustomDialogShow
        isHintVersion = builder.isHintVersion
        isUpdateDownloadWithBrowser = builder.isUpdateDownloadWithBrowser
        checkUpdateCommonUrl = builder.checkUpdateCommonUrl

        provider = builder.provider
        sslParams = builder.sslParams

        isUpdateTest = builder.isUpdateTest

        themeColor = builder.themeColor
        progressDrawable = builder.progressDrawable
        uploadImage = builder.uploadImage
        noNetImage = builder.noNetImage
    }

    fun getBuilder(): Builder {
        return builder
    }

    fun updateVersion(httpClient: HttpClient?) {
        if (httpClient == null) {
            Log.e("UpdateVersion", "没有初始化网络请求客户端")
            return
        }
        httpClient.getVersionInfo(checkUpdateCommonUrl, this)
    }

    class Builder {
        var isAutoInstall = true
            private set
        var isProgressDialogShow = true
            private set
        var isNotificationShow = false
            private set

        var isUpdateDialogShow = true
            private set
        var isNetCustomDialogShow = true
            private set
        var isHintVersion = true
            private set
        var isUpdateDownloadWithBrowser = false
            private set
        var checkUpdateCommonUrl = ""
            private set

        var provider = BuildConfig.LIBRARY_PACKAGE_NAME + ".FileProvider"
            private set
        var sslParams: SSLUtils.SSLParams = sslSocketFactory
            private set

        var isUpdateTest = false
            private set

        @ColorRes
        var themeColor = R.color.version_theme_color
            private set

        @DrawableRes
        var progressDrawable = R.drawable.progressbar
            private set

        @DrawableRes
        var uploadImage = R.mipmap.upload_version_gengxin
            private set

        @DrawableRes
        var noNetImage = R.mipmap.upload_version_nonet
            private set

        private var updateVersion: UpdateVersion? = null

        fun isAutoInstall(autoInstall: Boolean): Builder {
            isAutoInstall = autoInstall
            return this
        }

        fun isProgressDialogShow(progressDialogShow: Boolean): Builder {
            isProgressDialogShow = progressDialogShow
            return this
        }

        fun isNotificationShow(isNotificationShow: Boolean): Builder {
            this.isNotificationShow = isNotificationShow
            return this
        }

        fun isUpdateDialogShow(updateDialogShow: Boolean): Builder {
            isUpdateDialogShow = updateDialogShow
            return this
        }

        fun isNetCustomDialogShow(netCustomDialogShow: Boolean): Builder {
            isNetCustomDialogShow = netCustomDialogShow
            return this
        }

        fun isHintVersion(hintVersion: Boolean): Builder {
            isHintVersion = hintVersion
            return this
        }

        fun isUpdateDownloadWithBrowser(updateDownloadWithBrowser: Boolean): Builder {
            isUpdateDownloadWithBrowser = updateDownloadWithBrowser
            return this
        }

        fun setCheckUpdateCommonUrl(checkUpdateCommonUrl: String): Builder {
            this.checkUpdateCommonUrl = checkUpdateCommonUrl
            return this
        }

        fun setProvider(provider: String): Builder {
            this.provider = provider
            return this
        }

        /**
         * description HTTPS 证书
         *
         * @param sslParams ssl 参数 SSLSocketFactory和HostnameVerifier
         */
        fun setSSLParams(sslParams: SSLUtils.SSLParams): Builder {
            this.sslParams = sslParams
            return this
        }

        fun isUpdateTest(updateTest: Boolean): Builder {
            isUpdateTest = updateTest
            return this
        }

        /**
         * description 主题色
         *
         * @param themeColor 主题色
         */
        fun setThemeColor(@ColorRes themeColor: Int): Builder {
            this.themeColor = themeColor
            return this
        }

        /**
         * description 进度条样式
         *
         * @param progressDrawable 进度条样式
         */
        fun setProgressDrawable(@DrawableRes progressDrawable: Int): Builder {
            this.progressDrawable = progressDrawable
            return this
        }

        /**
         * description 更新背景图
         *
         * @param uploadImage 更新图
         */
        fun setUploadImage(@DrawableRes uploadImage: Int): Builder {
            this.uploadImage = uploadImage
            return this
        }

        /**
         * description 无网络背景图
         *
         * @param noNetImage 无网络图
         */
        fun setNoNetImage(@DrawableRes noNetImage: Int): Builder {
            this.noNetImage = noNetImage
            return this
        }

        fun build(): UpdateVersion {
            if (updateVersion == null) {
                updateVersion = UpdateVersion(this)
            } else {
                updateVersion!!.setBuilder(this)
            }
            return updateVersion!!
        }
    }

    companion object {
        var isAutoInstall = false
        var isProgressDialogShow = false
        var isNotificationShow = false

        var isUpdateDialogShow = false
            private set
        var isNetCustomDialogShow = false
            private set
        var isHintVersion = false
            private set
        var isUpdateDownloadWithBrowser = false
            private set
        var checkUpdateCommonUrl: String? = null
            private set
        var provider: String? = null
            private set
        var sslParams: SSLUtils.SSLParams? = null
            private set

        var isUpdateTest = false
            private set

        @ColorRes
        var themeColor = 0
            private set

        @DrawableRes
        var progressDrawable = 0
            private set

        @DrawableRes
        var uploadImage = 0
            private set

        @DrawableRes
        var noNetImage = 0
            private set
    }

    init {
        initParams(builder)
    }
}