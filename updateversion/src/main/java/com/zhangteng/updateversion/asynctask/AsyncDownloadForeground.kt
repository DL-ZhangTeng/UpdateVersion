package com.zhangteng.updateversion.asynctask

import com.zhangteng.updateversion.UpdateVersion
import com.zhangteng.updateversion.config.Constant
import com.zhangteng.updateversion.entity.VersionEntity
import com.zhangteng.utils.SSLUtils.UnSafeHostnameVerifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Created by swing on 2018/5/14.
 */
abstract class AsyncDownloadForeground : AsyncTask<VersionEntity, Long, Boolean>() {
    private var total: Long = 0
    private var apkFile: File? = null

    /**
     * 开始下载前的准备工作
     */
    abstract fun doOnPreExecute()

    /**
     * 下载完成后发送安装请求
     */
    abstract fun doOnPostExecute(flag: Boolean?)

    /**
     * 从背景任务中获取apk大小及下载完成后的文件对象
     */
    abstract fun doDoInBackground(total: Long, apkFile: File?)

    /**
     * 下载进度监听
     */
    abstract fun doOnProgressUpdate(vararg values: Long?)

    override fun onPreExecute() {
        doOnPreExecute()
    }

    override suspend fun doInBackground(vararg params: VersionEntity?): Boolean? {
        var url: URL? = null
        var urlConnection: HttpURLConnection? = null
        try {
            url = URL(params[0]?.url)
            urlConnection = url.openConnection() as HttpURLConnection
            if (urlConnection is HttpsURLConnection) {
                urlConnection.sslSocketFactory =
                    UpdateVersion.sslParams?.sSLSocketFactory
                urlConnection.hostnameVerifier = UnSafeHostnameVerifier
            }
            // 2.2版本以上HttpURLConnection跟服务交互采用了"gzip"压缩，添加这行代码避免total = -1
            urlConnection.setRequestProperty("Accept-Encoding", "identity")
            //设置超时间为3秒
            urlConnection.connectTimeout = 5 * 1000
            //防止屏蔽程序抓取而返回403错误
            urlConnection.setRequestProperty(
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"
            )
            if (urlConnection.responseCode != HttpURLConnection.HTTP_OK) {
                return false
            } else {
                //得到输入流
                val inputStream = urlConnection.inputStream
                total = urlConnection.contentLength.toLong()
                val appName = params[0]?.name ?: "app"
                val versionNo = params[0]?.versionNo ?: ""
                val apkName = "${appName}${versionNo}${Constant.SUFFIX}"
                Constant.cache[Constant.APP_NAME] = appName
                Constant.cache[Constant.APK_PATH] =
                    "${Constant.PATH}${File.separator}$appName${File.separator}$apkName"
                val savePath = File("${Constant.PATH}${File.separator}${appName}")
                if (!savePath.exists()) {
                    savePath.mkdirs()
                }
                //6.0以上系统需要静态动态权限不然无法创建文件
                apkFile = File(savePath, apkName)
                if (apkFile!!.exists()) {
                    apkFile!!.delete()
                    //return true;
                }
                doDoInBackground(total, apkFile)
                val fos = FileOutputStream(apkFile)
                // FileOutputStream fos = mContext.openFileOutput(apkFile.getAbsolutePath().toString(),mContext.MODE_PRIVATE);
                val buf = ByteArray(1024)
                var count = 0L
                var length = -1
                while (inputStream.read(buf).also { length = it } != -1) {
                    fos.write(buf, 0, length)
                    count += length
                    withContext(Dispatchers.Main) {
                        onProgressUpdate(count)
                    }
                }
                inputStream.close()
                fos.close()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            doDoInBackground(total, apkFile)
            return false
        } catch (e: IOException) {
            e.printStackTrace()
            doDoInBackground(total, apkFile)
            return false
        }
        return true
    }

    override fun onProgressUpdate(vararg values: Long?) {
        doOnProgressUpdate(*values)
    }

    override fun onPostExecute(result: Boolean?) {
        doOnPostExecute(result)
    }
}