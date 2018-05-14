package com.zhangteng.updateversionlibrary.config;

import android.os.Environment;

import java.util.HashMap;

/**
 * @author swing
 * @date 2018/5/14
 */
public class Constant {

    public static final String PATH = Environment
            .getExternalStorageDirectory().getPath();
    public static final String SUFFIX = ".apk";
    public static final String APK_PATH = "APK_PATH";
    public static final String APP_NAME = "APP_NAME";
    public static HashMap<String, String> cache = new HashMap<String, String>();
//    public static boolean IS_UPDATE_TEST = false;
//    public static boolean IS_UPDATE_DOWNLOAD_WITH_BROWSER = false;
//    public static boolean IS_AUTO_INSTALL = false;
//    public static boolean IS_UPDATE_DIALOG_SHOW = false;
//    public static boolean IS_HINT_VERSION = false;
//    public static String CHECK_UPDATE_COMMON_URL = "";
}
