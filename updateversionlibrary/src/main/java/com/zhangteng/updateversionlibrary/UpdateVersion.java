package com.zhangteng.updateversionlibrary;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.zhangteng.updateversionlibrary.callback.VersionInfoCallback;
import com.zhangteng.updateversionlibrary.http.HttpClient;

/**
 * Created by swing on 2018/5/11.
 */
public class UpdateVersion extends VersionInfoCallback {
    private static boolean isAutoInstall = false;
    private static boolean isUpdateDialogShow = false;
    private static boolean isHintVersion = false;
    private static boolean isUpdateTest = false;
    private static boolean isUpdateDownloadWithBrowser = false;
    private static boolean isNotificationShow = false;
    private static String checkUpdateCommonUrl = "";
    private Builder builder;

    public UpdateVersion(Builder builder) {
        this.builder = builder;
        initParams(builder);
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
        initParams(builder);
    }

    private void initParams(Builder builder) {
        isAutoInstall = builder.isAutoInstall;
        isUpdateDialogShow = builder.isUpdateDialogShow;
        isHintVersion = builder.isHintVersion;
        isUpdateTest = builder.isUpdateTest;
        isUpdateDownloadWithBrowser = builder.isUpdateDownloadWithBrowser;
        checkUpdateCommonUrl = builder.checkUpdateCommonUrl;
        isNotificationShow = builder.isNotificationShow;
    }

    public static boolean isAutoInstall() {
        return isAutoInstall;
    }

    public static boolean isUpdateDialogShow() {
        return isUpdateDialogShow;
    }

    public static boolean isHintVersion() {
        return isHintVersion;
    }

    public static boolean isUpdateTest() {
        return isUpdateTest;
    }

    public static boolean isUpdateDownloadWithBrowser() {
        return isUpdateDownloadWithBrowser;
    }

    public static String getCheckUpdateCommonUrl() {
        return checkUpdateCommonUrl;
    }

    public static boolean isNotificationShow() {
        return isNotificationShow;
    }

    public Builder getBuilder() {
        return builder;
    }

    public void updateVersion(HttpClient httpClient) {
        if (httpClient == null) {
            Log.e("UpdateVersion", "没有初始化网络请求客户端");
            return;
        }
        httpClient.getVersionInfo(UpdateVersion.getCheckUpdateCommonUrl(), this);
    }

    public static class Builder {
        private boolean isAutoInstall = false;
        private boolean isUpdateDialogShow = false;
        private boolean isHintVersion = false;
        private boolean isUpdateTest = false;
        private boolean isUpdateDownloadWithBrowser = false;
        private boolean isNotificationShow = false;
        private String checkUpdateCommonUrl = "";
        private UpdateVersion updateVersion;

        public Builder isAutoInstall(boolean autoInstall) {
            isAutoInstall = autoInstall;
            return this;
        }

        public Builder isUpdateDialogShow(boolean updateDialogShow) {
            isUpdateDialogShow = updateDialogShow;
            return this;
        }

        public Builder isHintVersion(boolean hintVersion) {
            isHintVersion = hintVersion;
            return this;
        }

        public Builder isUpdateTest(boolean updateTest) {
            isUpdateTest = updateTest;
            return this;
        }

        public Builder isUpdateDownloadWithBrowser(boolean updateDownloadWithBrowser) {
            isUpdateDownloadWithBrowser = updateDownloadWithBrowser;
            return this;
        }

        public Builder isCheckUpdateCommonUrl(String checkUpdateCommonUrl) {
            this.checkUpdateCommonUrl = checkUpdateCommonUrl;
            return this;
        }

        public Builder isNotificationShow(boolean isNotificationShow) {
            this.isNotificationShow = isNotificationShow;
            return this;
        }

        public UpdateVersion build() {
            if (updateVersion == null) {
                updateVersion = new UpdateVersion(this);
            } else {
                updateVersion.setBuilder(this);
            }
            return updateVersion;
        }
    }
}
