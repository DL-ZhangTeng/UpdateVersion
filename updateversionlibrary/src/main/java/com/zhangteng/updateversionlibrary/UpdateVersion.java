package com.zhangteng.updateversionlibrary;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
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
    private static String provider = BuildConfig.LIBRARY_PACKAGE_NAME + ".FileProvider";
    @ColorRes
    private static int themeColor = R.color.version_theme_color;
    @DrawableRes
    private static int uploadImage = R.mipmap.upload_version_gengxin;
    @DrawableRes
    private static int noNetImage = R.mipmap.upload_version_nonet;
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
        themeColor = builder.themeColor;
        uploadImage = builder.uploadImage;
        noNetImage = builder.noNetImage;
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

    public static int getThemeColor() {
        return themeColor;
    }

    public static int getUploadImage() {
        return uploadImage;
    }

    public static int getNoNetImage() {
        return noNetImage;
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
        private String provider = BuildConfig.LIBRARY_PACKAGE_NAME + ".FileProvider";
        @ColorRes
        private int themeColor = R.color.version_theme_color;
        @DrawableRes
        private int uploadImage = R.mipmap.upload_version_gengxin;
        @DrawableRes
        private int noNetImage = R.mipmap.upload_version_nonet;

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

        public Builder setThemeColor(@ColorRes int themeColor) {
            this.themeColor = themeColor;
            return this;
        }

        public Builder setUploadImage(int uploadImage) {
            this.uploadImage = uploadImage;
            return this;
        }

        public Builder setNoNetImage(int noNetImage) {
            this.noNetImage = noNetImage;
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
