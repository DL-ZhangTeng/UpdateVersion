package com.zhangteng.updateversionlibrary.callback;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.zhangteng.updateversionlibrary.UpdateVersion;
import com.zhangteng.updateversionlibrary.config.Constant;
import com.zhangteng.updateversionlibrary.dialog.UpdateDialogFragment;
import com.zhangteng.updateversionlibrary.entity.VersionEntity;
import com.zhangteng.updateversionlibrary.http.HttpClient;
import com.zhangteng.updateversionlibrary.utils.NetWorkUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author swing 2018/5/14
 */
public class VersionInfoCallback {
    private static Context mContext;
    private VersionEntity versionEntity;
    private SharedPreferences preferences_update;
    private FragmentManager mFragmentManager;
    private HttpClient httpClient;

    /**
     * 开始获取版本信息前的准备工作
     */
    public void onPreExecute(Context context, FragmentManager fragmentManager, HttpClient httpClient) {
        mContext = context;
        preferences_update = mContext.getSharedPreferences("Updater",
                Context.MODE_PRIVATE);
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
        SharedPreferences.Editor editor = preferences_update.edit();
        if (mContext != null && versionEntity != null) {
            Log.i("auto update", "versionEntity versioncode: " + versionEntity.getVersionNo() + " package versioncode: " + getPackageInfo().versionCode);
            if (versionEntity.getVersionCode() > getPackageInfo().versionCode) {
                if (UpdateVersion.isUpdateDialogShow()) {
                    showUpdateUICustom(versionEntity);
                }
                editor.putBoolean("hasNewVersion", true);
                editor.putString("lastestVersionCode",
                        "" + versionEntity.getVersionCode());
                editor.putString("lastestVersionName",
                        versionEntity.getVersionNo());
            } else {
                if (UpdateVersion.isHintVersion()) {
                    Toast.makeText(mContext, "当前已是最新版", Toast.LENGTH_LONG).show();
                }
                editor.putBoolean("hasNewVersion", false);
            }
        } else {
            if (UpdateVersion.isHintVersion()) {
                Toast.makeText(mContext, "当前已是最新版", Toast.LENGTH_LONG).show();
            }
        }
        editor.putString("currentVersionCode", getPackageInfo().versionCode
                + "");
        editor.putString("currentVersionName", getPackageInfo().versionName);
        editor.commit();
    }

    /**
     * 获取当前app版本
     *
     * @return
     * @throws PackageManager.NameNotFoundException
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
        dialogFragment.setTitle("发现新版本，是否更新 ？");
        dialogFragment.setNegativeBtn("下次再说", new UpdateDialogFragment.OnClickListener() {

            @Override
            public void onClick() {
                dialogFragment.dismiss();
            }
        });
        dialogFragment.setPositiveBtn("下载", new UpdateDialogFragment.OnClickListener() {

            @Override
            public void onClick() {
                dialogFragment.dismiss();

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

            }
        });
        dialogFragment.show(mFragmentManager, "");
    }

    /**
     * 手机网络dialog
     */
    private void showNetCustomDialog(final VersionEntity versionEntity) {

        final UpdateDialogFragment dialogFragment = new UpdateDialogFragment();
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.setTitle("您在目前的网络环境下继续下载将可能会消耗手机流量，请确认是否继续下载？");
        dialogFragment.setNegativeBtn("取消下载", new UpdateDialogFragment.OnClickListener() {

            @Override
            public void onClick() {
                dialogFragment.dismiss();
            }
        });
        dialogFragment.setPositiveBtn("继续下载", new UpdateDialogFragment.OnClickListener() {

            @Override
            public void onClick() {
                dialogFragment.dismiss();
                if (!UpdateVersion.isUpdateDownloadWithBrowser()) {
                    httpClient.downloadApk(versionEntity, new DownloadCallback());
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(versionEntity.getUrl()));
                    mContext.startActivity(i);
                }
            }
        });
        dialogFragment.show(mFragmentManager, "");
    }

    public static InputStream nativeAssertGet(String URL) {
        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open(URL);

        } catch (Exception e) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return inputStream;
    }
}
