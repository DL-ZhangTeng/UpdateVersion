package com.zhangteng.updateversion.entity

/**
 * 版本信息实体
 * Created by swing on 2018/5/11.
 */
class VersionEntity {
    /**
     * 版本主键
     */
    var id: String? = null

    /**
     * app名称
     */
    var name: String? = null

    /**
     * 应用id
     */
    var appId: String? = null

    /**
     * 版本号
     */
    var versionNo: String? = null

    /**
     * 版本code
     */
    var versionCode = 0L

    /**
     * 下载地址
     */
    var url: String? = null

    /**
     * 更新标题
     */
    var title: String? = null

    /**
     * 更新内容
     */
    var updateDesc: String? = null

    /**
     * 是否强制更新0否1是
     */
    var forceUpdate = 0
}