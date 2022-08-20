package com.zhangteng.updateversionlibrary;

import android.util.Log;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.zhangteng.updateversionlibrary.callback.VersionInfoCallback;
import com.zhangteng.updateversionlibrary.http.HttpClient;
import com.zhangteng.utils.SSLUtils;

/**
 * Created by swing on 2018/5/11.
 */
public class UpdateVersion extends VersionInfoCallback {
    private static boolean isAutoInstall;
    private static boolean isUpdateDialogShow;
    private static boolean isNetCustomDialogShow;
    private static boolean isProgressDialogShow;
    private static boolean isHintVersion;
    private static boolean isUpdateTest;
    private static boolean isUpdateDownloadWithBrowser;
    private static boolean isNotificationShow;

    private static String checkUpdateCommonUrl;
    private static SSLUtils.SSLParams sslParams;

    private static String provider;
    @ColorRes
    private static int themeColor;
    @DrawableRes
    private static int progressDrawable;
    @DrawableRes
    private static int uploadImage;
    @DrawableRes
    private static int noNetImage;

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
        isNetCustomDialogShow = builder.isNetCustomDialogShow;
        isProgressDialogShow = builder.isProgressDialogShow;
        isHintVersion = builder.isHintVersion;
        isUpdateTest = builder.isUpdateTest;
        isUpdateDownloadWithBrowser = builder.isUpdateDownloadWithBrowser;
        isNotificationShow = builder.isNotificationShow;
        checkUpdateCommonUrl = builder.checkUpdateCommonUrl;
        sslParams = builder.sslParams;
        provider = builder.provider;
        themeColor = builder.themeColor;
        progressDrawable = builder.progressDrawable;
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

    public static boolean isNetCustomDialogShow() {
        return isNetCustomDialogShow;
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

    public static boolean isNotificationShow() {
        return isNotificationShow;
    }

    public static String getCheckUpdateCommonUrl() {
        return checkUpdateCommonUrl;
    }

    public static SSLUtils.SSLParams getSslParams() {
        return sslParams;
    }

    public static String getProvider() {
        return provider;
    }

    public static int getThemeColor() {
        return themeColor;
    }

    public static int getProgressDrawable() {
        return progressDrawable;
    }

    public static int getUploadImage() {
        return uploadImage;
    }

    public static int getNoNetImage() {
        return noNetImage;
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
        private boolean isAutoInstall = true;
        private boolean isUpdateDialogShow = true;
        private boolean isNetCustomDialogShow = true;
        private boolean isProgressDialogShow = true;
        private boolean isHintVersion = true;
        private boolean isUpdateTest = false;
        private boolean isUpdateDownloadWithBrowser = false;
        private boolean isNotificationShow = false;

        private String checkUpdateCommonUrl = "";
        private SSLUtils.SSLParams sslParams = SSLUtils.INSTANCE.getSslSocketFactory();

        private String provider = BuildConfig.LIBRARY_PACKAGE_NAME + ".FileProvider";
        @ColorRes
        private int themeColor = R.color.version_theme_color;
        @DrawableRes
        private int progressDrawable = R.drawable.progressbar;
        @DrawableRes
        private int uploadImage = R.mipmap.upload_version_gengxin;
        @DrawableRes
        private int noNetImage = R.mipmap.upload_version_nonet;

        private UpdateVersion updateVersion;

        public Builder isAutoInstall(boolean autoInstall) {
            isAutoInstall = autoInstall;
            return this;
        }

        public Builder isUpdateDialogShow(boolean updateDialogShow) {
            isUpdateDialogShow = updateDialogShow;
            return this;
        }

        public Builder isNetCustomDialogShow(boolean netCustomDialogShow) {
            isNetCustomDialogShow = netCustomDialogShow;
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

        public Builder isNotificationShow(boolean isNotificationShow) {
            this.isNotificationShow = isNotificationShow;
            return this;
        }

        public Builder isCheckUpdateCommonUrl(String checkUpdateCommonUrl) {
            this.checkUpdateCommonUrl = checkUpdateCommonUrl;
            return this;
        }

        /**
         * description HTTPS 证书
         *
         * @param sslParams ssl 参数 SSLSocketFactory和HostnameVerifier
         */
        public Builder setSSLParams(SSLUtils.SSLParams sslParams) {
            this.sslParams = sslParams;
            return this;
        }

        public Builder setProvider(String provider) {
            this.provider = provider;
            return this;
        }

        /**
         * description 主题色
         *
         * @param themeColor 主题色
         */
        public Builder setThemeColor(@ColorRes int themeColor) {
            this.themeColor = themeColor;
            return this;
        }

        /**
         * description 进度条样式
         *
         * @param progressDrawable 进度条样式
         */
        public Builder setProgressDrawable(@DrawableRes int progressDrawable) {
            this.progressDrawable = progressDrawable;
            return this;
        }

        /**
         * description 更新背景图
         *
         * @param uploadImage 更新图
         */
        public Builder setUploadImage(@DrawableRes int uploadImage) {
            this.uploadImage = uploadImage;
            return this;
        }

        /**
         * description 无网络背景图
         *
         * @param noNetImage 无网络图
         */
        public Builder setNoNetImage(@DrawableRes int noNetImage) {
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
