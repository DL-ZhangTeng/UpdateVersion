package com.zhangteng.updateversionlibrary.utils;

import com.zhangteng.updateversionlibrary.UpdateVersion;
import com.zhangteng.updateversionlibrary.entity.VersionEntity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by swing on 14-5-8.
 */
public class JSONHandler {

    public static VersionEntity toVersionEntity(InputStream is) throws Exception {
        if (is == null) {
            return null;
        }
        String byteData = new String(readStream(is), "utf-8");
        is.close();
        JSONObject result = new JSONObject(byteData);
        if (null != result.getJSONObject("result")) {
            JSONObject jsonObject = result.getJSONObject("result");
            VersionEntity versionEntity = new VersionEntity();
            if (UpdateVersion.isUpdateDownloadWithBrowser()) {
                versionEntity.setUrl(UpdateVersion.getCheckUpdateCommonUrl());
            } else {
                versionEntity.setUrl(jsonObject.getString("url"));
            }
            versionEntity.setId(jsonObject.getString("id"));
            versionEntity.setName(jsonObject.getString("name"));
            versionEntity.setAppId(jsonObject.getString("appId"));
            versionEntity.setVersionNo(jsonObject.getString("versionNo"));
            versionEntity.setVersionCode(jsonObject.getInt("versionCode"));
            versionEntity.setUpdateDesc(jsonObject.getString("updateDesc"));
            versionEntity.setForceUpdate(jsonObject.getInt("forceUpdate"));
            return versionEntity;
        } else {
            return null;
        }


    }

    private static byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] array = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(array)) != -1) {
            outputStream.write(array, 0, len);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

}
