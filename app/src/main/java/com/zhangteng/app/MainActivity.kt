package com.zhangteng.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zhangteng.androidpermission.AndroidPermission.Buidler
import com.zhangteng.androidpermission.Permission
import com.zhangteng.androidpermission.callback.Callback
import com.zhangteng.updateversion.UpdateVersion
import com.zhangteng.updateversion.callback.DownloadCallback
import com.zhangteng.updateversion.callback.VersionInfoCallback
import com.zhangteng.updateversion.entity.VersionEntity
import com.zhangteng.updateversion.http.CommonHttpClient
import com.zhangteng.utils.SSLUtils.sslSocketFactory
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val androidPermission = Buidler()
            .with(this)
            .permission(*Permission.Group.STORAGE)
            .callback(object : Callback {
                override fun success(permissionActivity: Activity) {}
                override fun failure(permissionActivity: Activity) {}
                override fun nonExecution(permissionActivity: Activity) {}
            })
            .build()
        androidPermission.execute()
    }

    fun onClick1(view: View?) {
        UpdateVersion.Builder()
            //是否为调试模式
            .isUpdateTest(true)
            //通知栏显示
            .isNotificationShow(true)
            //是否自动安装
            .isAutoInstall(true)
            //是否提示更新信息
            .isHintVersion(true)
            //是否显示更新dialog
            .isUpdateDialogShow(true)
            //是否显示移动网络提示dialog
            .isNetCustomDialogShow(true)
            //是否显示下载进度dialog
            .isProgressDialogShow(true)
            //是否使用浏览器更新
            .isUpdateDownloadWithBrowser(false)
            //获取服务器的版本信息
            .setCheckUpdateCommonUrl("http://")
            .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
            .build()
            //执行更新任务
            .updateVersion(CommonHttpClient(this, this.supportFragmentManager))
    }

    fun onClick2(view: View?) {
        UpdateVersion.Builder()
            //是否为调试模式
            .isUpdateTest(true)
            //通知栏显示
            .isNotificationShow(false)
            //是否自动安装
            .isAutoInstall(true)
            //是否提示更新信息
            .isHintVersion(true)
            //是否显示更新dialog
            .isUpdateDialogShow(true)
            //是否显示移动网络提示dialog
            .isNetCustomDialogShow(true)
            //是否显示下载进度dialog
            .isProgressDialogShow(true)
            //是否使用浏览器更新
            .isUpdateDownloadWithBrowser(false)
            //获取服务器的版本信息
            .setCheckUpdateCommonUrl("http://")
            .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
            .build()
            //执行更新任务
            .updateVersion(CommonHttpClient(this, this.supportFragmentManager))
    }

    fun onClick3(view: View?) {
        UpdateVersion.Builder()
            //是否为调试模式
            .isUpdateTest(true)
            //通知栏显示
            .isNotificationShow(true)
            //是否自动安装
            .isAutoInstall(false)
            //是否提示更新信息
            .isHintVersion(true)
            //是否显示更新dialog
            .isUpdateDialogShow(true)
            //是否显示移动网络提示dialog
            .isNetCustomDialogShow(true)
            //是否显示下载进度dialog
            .isProgressDialogShow(true)
            //是否使用浏览器更新
            .isUpdateDownloadWithBrowser(false)
            //获取服务器的版本信息
            .setCheckUpdateCommonUrl("http://")
            .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
            .build()
            //执行更新任务
            .updateVersion(CommonHttpClient(this, this.supportFragmentManager))
    }

    fun onClick4(view: View?) {
        UpdateVersion.Builder()
            //是否为调试模式
            .isUpdateTest(true)
            //通知栏显示
            .isNotificationShow(false)
            //是否自动安装
            .isAutoInstall(false)
            //是否提示更新信息
            .isHintVersion(true)
            //是否显示更新dialog
            .isUpdateDialogShow(true)
            //是否显示移动网络提示dialog
            .isNetCustomDialogShow(true)
            //是否显示下载进度dialog
            .isProgressDialogShow(true)
            //是否使用浏览器更新
            .isUpdateDownloadWithBrowser(false)
            //获取服务器的版本信息
            .setCheckUpdateCommonUrl("http://")
            .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
            .setThemeColor(R.color.colorPrimary)
            .setProgressDrawable(R.drawable.progressbar_self)
            .setNoNetImage(R.mipmap.upload_version_gengxin)
            .setUploadImage(R.mipmap.upload_version_nonet)
            .build()
            //执行更新任务
            .updateVersion(CommonHttpClient(this, this.supportFragmentManager))
    }

    fun onClick5(view: View?) {
        UpdateVersion.Builder()
            //是否为调试模式
            .isUpdateTest(true)
            //通知栏显示
            .isNotificationShow(false)
            //是否自动安装
            .isAutoInstall(true)
            //是否提示更新信息
            .isHintVersion(true)
            //是否显示更新dialog
            .isUpdateDialogShow(true)
            //是否显示移动网络提示dialog
            .isNetCustomDialogShow(true)
            //是否显示下载进度dialog
            .isProgressDialogShow(true)
            //是否使用浏览器更新
            .isUpdateDownloadWithBrowser(false)
            //获取服务器的版本信息
            .setCheckUpdateCommonUrl("http://")
            .setSSLParams(sslSocketFactory)
            .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
            .setThemeColor(R.color.colorPrimary)
            .setProgressDrawable(R.drawable.progressbar_self)
            .build()
            //执行更新任务
            .updateVersion(object : CommonHttpClient(this, supportFragmentManager) {
                override fun getVersionInfo(
                    versionInfoUrl: String?,
                    versionInfoCallback: VersionInfoCallback
                ) {
                    versionInfoCallback.onPreExecute(
                        this@MainActivity,
                        supportFragmentManager,
                        this
                    )
                    updateVersion(versionInfoCallback)
                } //                    @Override
                //                    public void downloadApk(VersionEntity versionEntity, DownloadCallback downloadCallback) {
                //                        downloadCallback.onPreExecute(MainActivity.this);
                //                        MainActivity.this.downloadApk(versionEntity, downloadCallback);
                //                    }
            })
    }

    /**
     * 网络下载
     */
    private fun downloadApk(versionEntity: VersionEntity, downloadCallback: DownloadCallback) {
        downloadCallback.doInBackground(100, File("/"))
        downloadCallback.onProgressUpdate(100)
        downloadCallback.onPostExecute(true)
    }

    /**
     * 网络请求
     */
    private fun updateVersion(versionInfoCallback: VersionInfoCallback) {
        val versionEntity = VersionEntity()
        versionEntity.url = "https://tp.kaishuihu.com/apk/fdy_1-1.0.0-2021-12-23.apk"
        versionEntity.versionNo = "2.0"
        versionEntity.versionCode = 2
        versionInfoCallback.doInBackground(versionEntity)
        versionInfoCallback.onPostExecute()
    }
}