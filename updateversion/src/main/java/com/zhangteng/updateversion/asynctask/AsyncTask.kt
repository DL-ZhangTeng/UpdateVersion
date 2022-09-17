package com.zhangteng.updateversion.asynctask

/**
 * description: 异步任务android.os.AsyncTask替代品
 * author: Swing
 * date: 2022/9/17
 */
abstract class AsyncTask<Params, Progress, Result> {
    /**
     * 准备执行
     */
    abstract fun onPreExecute()

    /**
     * 执行
     *
     */
    abstract suspend fun doInBackground(vararg params: Params?): Result?

    /**
     * 刷新进度
     */
    open fun onProgressUpdate(vararg values: Progress?) {

    }

    /**
     * 任务完成
     */
    abstract fun onPostExecute(result: Result?)
}