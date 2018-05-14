package com.zhangteng.updateversion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhangteng.updateversionlibrary.UpdateVersion;
import com.zhangteng.updateversionlibrary.http.CommonHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                .isCheckUpdateCommonUrl("http://221.235.188.36:51022/download/software/android/quanyouxue_student_hg.apk")
                //是否提示更新信息
                .isHintVersion(true)
                //是否显示更新dialog
                .isUpdateDialogShow(true)
                //是否使用浏览器更新
                .isUpdateDownloadWithBrowser(false)
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
                .isCheckUpdateCommonUrl("http://221.235.188.36:51022/download/software/android/quanyouxue_student_hg.apk")
                //是否提示更新信息
                .isHintVersion(true)
                //是否显示更新dialog
                .isUpdateDialogShow(true)
                //是否使用浏览器更新
                .isUpdateDownloadWithBrowser(false)
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
                .isCheckUpdateCommonUrl("http://221.235.188.36:51022/download/software/android/quanyouxue_student_hg.apk")
                //是否提示更新信息
                .isHintVersion(true)
                //是否显示更新dialog
                .isUpdateDialogShow(true)
                //是否使用浏览器更新
                .isUpdateDownloadWithBrowser(false)
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
                .isCheckUpdateCommonUrl("http://221.235.188.36:51022/download/software/android/quanyouxue_student_hg.apk")
                //是否提示更新信息
                .isHintVersion(true)
                //是否显示更新dialog
                .isUpdateDialogShow(true)
                //是否使用浏览器更新
                .isUpdateDownloadWithBrowser(false)
                .build()
                //执行更新任务
                .updateVersion(new CommonHttpClient(this, this.getSupportFragmentManager()));
    }
}
