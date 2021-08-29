package com.zhangteng.updateversionlibrary.entity;

/**
 * 版本信息实体
 * Created by swing on 2018/5/11.
 */
public class VersionEntity {
    /**
     * 版本主键
     */
    private String id;
    /**
     * app名称
     */
    private String name;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 版本号
     */
    private String versionNo;
    /**
     * 版本code
     */
    private int versionCode;
    /**
     * 下载地址
     */
    private String url;
    /**
     * 更新标题
     */
    private String title;
    /**
     * 更新内容
     */
    private String updateDesc;
    /**
     * 是否强制更新0否1是
     */
    private int forceUpdate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public int getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
