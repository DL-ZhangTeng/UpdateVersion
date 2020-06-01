package com.zhangteng.updateversionlibrary.callback;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.zhangteng.updateversionlibrary.UpdateVersion;
import com.zhangteng.updateversionlibrary.dialog.UpdateDialogFragment;
import com.zhangteng.updateversionlibrary.entity.VersionEntity;
import com.zhangteng.updateversionlibrary.http.HttpClient;
import com.zhangteng.updateversionlibrary.utils.NetWorkUtils;

import java.io.InputStream;

/**
 * @author swing 2018/5/14
 */
public class VersionInfoCallback {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private VersionEntity versionEntity;
    private FragmentManager mFragmentManager;
    private HttpClient httpClient;

    /**
     * 开始获取版本信息前的准备工作
     */
    public void onPreExecute(Context context, FragmentManager fragmentManager, HttpClient httpClient) {
        mContext = context;
        this.mFragmentManager = fragmentManager;
        this.httpClient = httpClient;
    }

    /**
     * 从背景任务中获取版本信息
     */
    public void doInBackground(VersionEntity versionEntity) {
        this.versionEntity = versionEntity;
    }

    /**
     * 请求完成后进行下载请求
     */
    public void onPostExecute() {
        if (mContext != null && versionEntity != null) {
            Log.i("auto update", "versionEntity versioncode: " + versionEntity.getVersionNo() + " package versioncode: " + getPackageInfo().versionCode);
            if (versionEntity.getVersionCode() > getPackageInfo().versionCode) {
                if (versionEntity.getForceUpdate() != 0) {
                    UpdateVersion.setIsAutoInstall(true);
                    UpdateVersion.setIsProgressDialogShow(true);
                    NetWorkUtils netWorkUtils = new NetWorkUtils(mContext);
                    int type = netWorkUtils.getNetType();
                    if (type != 1) {
                        showUpdateUICustom(versionEntity);
                    } else {
                        httpClient.downloadApk(versionEntity, new DownloadCallback());
                    }
                } else {
                    if (UpdateVersion.isUpdateDialogShow()) {
                        showUpdateUICustom(versionEntity);
                    }
                }
            } else {
                if (UpdateVersion.isHintVersion()) {
                    Toast.makeText(mContext, "当前已是最新版", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            if (UpdateVersion.isHintVersion()) {
                Toast.makeText(mContext, "当前已是最新版", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 获取当前app版本
     */
    private PackageInfo getPackageInfo() {
        PackageInfo pinfo = null;
        if (mContext != null) {
            try {
                pinfo = mContext.getPackageManager().getPackageInfo(
                        mContext.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pinfo;
    }

    /**
     * 更新提示
     */
    private void showUpdateUICustom(final VersionEntity versionEntity) {
        final UpdateDialogFragment dialogFragment = new UpdateDialogFragment();
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.setTitle("发现新版本\n" + versionEntity.getVersionNo());
        dialogFragment.setContentTitleText("更新的内容");
        dialogFragment.setContentText(versionEntity.getUpdateDesc());
        dialogFragment.setNegativeBtn("暂不", null);
        dialogFragment.setPositiveBtn("立即更新", () -> {
            NetWorkUtils netWorkUtils = new NetWorkUtils(mContext);
            int type = netWorkUtils.getNetType();
            if (type != 1) {
                showNetCustomDialog(versionEntity);
            } else {
                if (!UpdateVersion.isUpdateDownloadWithBrowser()) {
                    httpClient.downloadApk(versionEntity, new DownloadCallback());
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(versionEntity.getUrl()));
                    mContext.startActivity(i);
                }
            }
        });
        try {
            dialogFragment.show(mFragmentManager, "");
        } catch (IllegalStateException e) {
            Log.e("UpdateDialogFragment", e.getMessage());
        }
    }

    /**
     * 手机网络dialog
     */
    private void showNetCustomDialog(final VersionEntity versionEntity) {
        final UpdateDialogFragment dialogFragment = new UpdateDialogFragment();
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.setContentText("小主，当前在无WIFI的情况下下载，请确定是否使用流量继续下载。");
        dialogFragment.setNetHint(true);
        dialogFragment.setNegativeBtn("取消", null);
        dialogFragment.setPositiveBtn("继续下载", () -> {
            if (!UpdateVersion.isUpdateDownloadWithBrowser()) {
                httpClient.downloadApk(versionEntity, new DownloadCallback());
            } else {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(versionEntity.getUrl()));
                mContext.startActivity(i);
            }
        });
        try {
            dialogFragment.show(mFragmentManager, "");
        } catch (IllegalStateException e) {
            Log.e("UpdateDialogFragment", e.getMessage());
        }
    }

    public static InputStream nativeAssertGet(String URL) {
        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
