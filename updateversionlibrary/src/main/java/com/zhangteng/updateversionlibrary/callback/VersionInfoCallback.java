package com.zhangteng.updateversionlibrary.callback;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.zhangteng.updateversionlibrary.R;
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
                    Toast.makeText(mContext, mContext == null ? "当前已是最新版" : mContext.getString(R.string.version_hint), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            if (UpdateVersion.isHintVersion()) {
                Toast.makeText(mContext, mContext == null ? "当前已是最新版" : mContext.getString(R.string.version_hint), Toast.LENGTH_LONG).show();
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
    @SuppressLint("WrongConstant")
    private void showUpdateUICustom(final VersionEntity versionEntity) {
        final UpdateDialogFragment dialogFragment = new UpdateDialogFragment();
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.setTitle(mContext == null ? "发现新版本%s" : String.format(mContext.getString(R.string.version_title), "\n" + versionEntity.getVersionNo()));
        dialogFragment.setContentTitleText(mContext == null ? "更新的内容" : TextUtils.isEmpty(versionEntity.getTitle()) ? mContext.getString(R.string.version_content_title) : versionEntity.getTitle());
        dialogFragment.setContentText(mContext == null ? "%s" : String.format(mContext.getString(R.string.version_content), versionEntity.getUpdateDesc()));
        dialogFragment.setNegativeBtn(mContext == null ? "暂不" : mContext.getString(R.string.version_cancel), null);
        dialogFragment.setPositiveBtn(mContext == null ? "立即更新" : mContext.getString(R.string.version_confirm), () -> {
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
    @SuppressLint("WrongConstant")
    private void showNetCustomDialog(final VersionEntity versionEntity) {
        final UpdateDialogFragment dialogFragment = new UpdateDialogFragment();
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.setContentText(mContext == null ? "当前在无WIFI的情况下下载，请确定是否使用流量继续下载。" : mContext.getString(R.string.no_wifi_hint));
        dialogFragment.setNetHint(true);
        dialogFragment.setNegativeBtn(mContext == null ? "取消" : mContext.getString(R.string.no_wifi_hint_cancel), null);
        dialogFragment.setPositiveBtn(mContext == null ? "继续下载" : mContext.getString(R.string.no_wifi_hint_confirm), () -> {
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
