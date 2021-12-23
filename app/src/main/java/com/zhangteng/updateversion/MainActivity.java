package com.zhangteng.updateversion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zhangteng.androidpermission.AndroidPermission;
import com.zhangteng.androidpermission.Permission;
import com.zhangteng.androidpermission.callback.Callback;
import com.zhangteng.updateversionlibrary.UpdateVersion;
import com.zhangteng.updateversionlibrary.callback.DownloadCallback;
import com.zhangteng.updateversionlibrary.callback.VersionInfoCallback;
import com.zhangteng.updateversionlibrary.entity.VersionEntity;
import com.zhangteng.updateversionlibrary.http.CommonHttpClient;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidPermission androidPermission = new AndroidPermission.Buidler()
                .with(this)
                .permission(Permission.Group.STORAGE)
                .callback(new Callback() {

                    @Override
                    public void success(Activity permissionActivity) {

                    }

                    @Override
                    public void failure(Activity permissionActivity) {

                    }

                    @Override
                    public void nonExecution(Activity permissionActivity) {

                    }
                })
                .build();
        androidPermission.execute();
    }

    public void onClick1(View view) {
        new UpdateVersion.Builder()
                //是否为调试模式
                .isUpdateTest(true)
                //通知栏显示
                .isNotificationShow(true)
                //是否自动安装
                .isAutoInstall(true)
                //获取服务器的版本信息
                .isCheckUpdateCommonUrl("http://")
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
                .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
                .build()
                //执行更新任务
                .updateVersion(new CommonHttpClient(this, this.getSupportFragmentManager()));
    }

    public void onClick2(View view) {
        new UpdateVersion.Builder()
                //是否为调试模式
                .isUpdateTest(true)
                //通知栏显示
                .isNotificationShow(false)
                //是否自动安装
                .isAutoInstall(true)
                //获取服务器的版本信息
                .isCheckUpdateCommonUrl("http://")
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
                .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
                .build()
                //执行更新任务
                .updateVersion(new CommonHttpClient(this, this.getSupportFragmentManager()));
    }

    public void onClick3(View view) {
        new UpdateVersion.Builder()
                //是否为调试模式
                .isUpdateTest(true)
                //通知栏显示
                .isNotificationShow(true)
                //是否自动安装
                .isAutoInstall(false)
                //获取服务器的版本信息
                .isCheckUpdateCommonUrl("http://")
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
                .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
                .build()
                //执行更新任务
                .updateVersion(new CommonHttpClient(this, this.getSupportFragmentManager()));
    }

    public void onClick4(View view) {
        new UpdateVersion.Builder()
                //是否为调试模式
                .isUpdateTest(true)
                //通知栏显示
                .isNotificationShow(false)
                //是否自动安装
                .isAutoInstall(false)
                //获取服务器的版本信息
                .isCheckUpdateCommonUrl("http://")
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
                .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
                .setThemeColor(R.color.colorPrimary)
                .setProgressDrawable(R.drawable.progressbar_self)
                .setNoNetImage(R.mipmap.upload_version_gengxin)
                .setUploadImage(R.mipmap.upload_version_nonet)
                .build()
                //执行更新任务
                .updateVersion(new CommonHttpClient(this, this.getSupportFragmentManager()));
    }

    public void onClick5(View view) {
        new UpdateVersion.Builder()
                //是否为调试模式
                .isUpdateTest(true)
                //通知栏显示
                .isNotificationShow(false)
                //是否自动安装
                .isAutoInstall(true)
                //获取服务器的版本信息
                .isCheckUpdateCommonUrl("http://")
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
                .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
                .setThemeColor(R.color.colorPrimary)
                .setProgressDrawable(R.drawable.progressbar_self)
                .build()
                //执行更新任务
                .updateVersion(new CommonHttpClient(this, getSupportFragmentManager()) {
                    @Override
                    public void getVersionInfo(String versionInfoUrl, VersionInfoCallback versionInfoCallback) {
                        versionInfoCallback.onPreExecute(MainActivity.this, getSupportFragmentManager(), this);
                        MainActivity.this.updateVersion(versionInfoCallback);

                    }
//                    @Override
//                    public void downloadApk(VersionEntity versionEntity, DownloadCallback downloadCallback) {
//                        downloadCallback.onPreExecute(MainActivity.this);
//                        MainActivity.this.downloadApk(versionEntity, downloadCallback);
//                    }
                });
    }

    /**
     * 网络下载
     */
    private void downloadApk(VersionEntity versionEntity, DownloadCallback downloadCallback) {
        downloadCallback.doInBackground(100, new File("/"));
        downloadCallback.onProgressUpdate(100);
        downloadCallback.onPostExecute(true);
    }

    /**
     * 网络请求
     */
    private void updateVersion(VersionInfoCallback versionInfoCallback) {
        VersionEntity versionEntity = new VersionEntity();
        versionEntity.setUrl("http://tp.kaishuihu.com/apk/fdy_1-1.0.0-2021-12-23.apk");
        versionEntity.setVersionNo("2.0");
        versionEntity.setVersionCode(2);
        versionInfoCallback.doInBackground(versionEntity);
        versionInfoCallback.onPostExecute();
    }

}
