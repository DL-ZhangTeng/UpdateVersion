    new UpdateVersion.Builder()
                //是否为调试模式
                .isUpdateTest(false)
                //是否自动安装
                .isAutoInstall(true)
                //获取服务器的版本信息
                .isCheckUpdateCommonUrl("http://baidu.com")
                //是否提示更新信息
                .isHintVersion(true)
                //是否显示更新dialog
                .isUpdateDialogShow(true)
                //是否使用浏览器更新
                .isUpdateDownloadWithBrowser(false)
                .build()
                //执行更新任务
                .updateVersion("", new CommonHttpClient(this, this.getSupportFragmentManager()));