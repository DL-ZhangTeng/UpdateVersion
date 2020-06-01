package com.zhangteng.updateversionlibrary;

import android.util.Log;

import com.zhangteng.updateversionlibrary.callback.VersionInfoCallback;
import com.zhangteng.updateversionlibrary.http.HttpClient;

/**
 * Created by swing on 2018/5/11.
 */
public class UpdateVersion extends VersionInfoCallback {
    private static boolean isAutoInstall = true;
    private static boolean isUpdateDialogShow = true;
    private static boolean isProgressDialogShow = true;
    private static boolean isHintVersion = true;
    private static boolean isUpdateTest = false;
    private static boolean isUpdateDownloadWithBrowser = false;
    private static boolean isNotificationShow = false;
    private static String checkUpdateCommonUrl = "";
    private static String provider = BuildConfig.APPLICATION_ID + ".FileProvider";
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
        isProgressDialogShow = builder.isProgressDialogShow;
        isHintVersion = builder.isHintVersion;
        isUpdateTest = builder.isUpdateTest;
        isUpdateDownloadWithBrowser = builder.isUpdateDownloadWithBrowser;
        checkUpdateCommonUrl = builder.checkUpdateCommonUrl;
        isNotificationShow = builder.isNotificationShow;
        provider = builder.provider;
    }

    public static boolean isAutoInstall() {
        return isAutoInstall;
    }

    public static void setIsAutoInstall(boolean isAutoInstall) {
        UpdateVersion.isAutoInstall = isAutoInstall;
    }

    public static void setIsProgressDialogShow(boolean isProgressDialogShow) {
        UpdateVersion.isProgressDialogShow = isProgressDialogShow;
    }

    public static boolean isUpdateDialogShow() {
        return isUpdateDialogShow;
    }

    public static boolean isProgressDialogShow() {
        return isProgressDialogShow;
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

    public static String getProvider() {
        return provider;
    }

    public void updateVersion(HttpClient httpClient) {
        if (httpClient == null) {
            Log.e("UpdateVersion", "没有初始化网络请求客户端");
            return;
        }
        httpClient.getVersionInfo(UpdateVersion.getCheckUpdateCommonUrl(), this);
    }

    public static class Builder {
        private boolean isAutoInstall = true;
        private boolean isUpdateDialogShow = true;
        private boolean isProgressDialogShow = true;
        private boolean isHintVersion = true;
        private boolean isUpdateTest = false;
        private boolean isUpdateDownloadWithBrowser = false;
        private boolean isNotificationShow = false;
        private String checkUpdateCommonUrl = "";
        private UpdateVersion updateVersion;
        private String provider = BuildConfig.APPLICATION_ID + ".FileProvider";

        public Builder isAutoInstall(boolean autoInstall) {
            isAutoInstall = autoInstall;
            return this;
        }

        public Builder isUpdateDialogShow(boolean updateDialogShow) {
            isUpdateDialogShow = updateDialogShow;
            return this;
        }

        public Builder isProgressDialogShow(boolean progressDialogShow) {
            isProgressDialogShow = progressDialogShow;
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

        public Builder setProvider(String provider) {
            this.provider = provider;
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
