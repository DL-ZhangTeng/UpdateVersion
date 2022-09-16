package com.zhangteng.updateversionlibrary.config;

import android.os.Environment;

import java.util.HashMap;

/**
 * @author swing 2018/5/14
 */
public class Constant {

    public static String PATH = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    public static final String SUFFIX = ".apk";
    public static final String APK_PATH = "APK_PATH";
    public static final String APP_NAME = "APP_NAME";
    public static HashMap<String, String> cache = new HashMap<String, String>();
}
