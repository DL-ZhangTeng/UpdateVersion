package com.zhangteng.updateversion.utils

import com.zhangteng.updateversion.entity.VersionEntity
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

/**
 * Created by swing on 14-5-8.
 */
object JSONHandler {
    @Throws(Exception::class)
    fun toVersionEntity(`is`: InputStream?): VersionEntity? {
        if (`is` == null) {
            return null
        }
        val byteData = String(readStream(`is`), StandardCharsets.UTF_8)
        `is`.close()
        val result = JSONObject(byteData)
        val jsonObject = result.getJSONObject("result")
        val versionEntity = VersionEntity()
        versionEntity.url = jsonObject.getString("url")
        versionEntity.id = jsonObject.getString("id")
        versionEntity.name = jsonObject.getString("name")
        versionEntity.appId = jsonObject.getString("appId")
        versionEntity.versionNo = jsonObject.getString("versionNo")
        versionEntity.versionCode = jsonObject.getInt("versionCode")
        versionEntity.updateDesc = jsonObject.getString("title")
        versionEntity.updateDesc = jsonObject.getString("updateDesc")
        versionEntity.forceUpdate = jsonObject.getInt("forceUpdate")
        return versionEntity
    }

    @Throws(IOException::class)
    private fun readStream(inputStream: InputStream): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val array = ByteArray(1024)
        var len = 0
        while (inputStream.read(array).also { len = it } != -1) {
            outputStream.write(array, 0, len)
        }
        inputStream.close()
        outputStream.close()
        return outputStream.toByteArray()
    }
}