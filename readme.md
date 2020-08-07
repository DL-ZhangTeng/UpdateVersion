# 应用内版本更新库UpdateVersion
UpdateVersion是一个Android版本更新库。
[GitHub仓库地址](https://github.com/duoluo9/UpdateVersion)
## 引入
### maven
```xml
<dependency>
	<groupId>com.zhangteng.updateversion</groupId>
	<artifactId>updateversionlibrary</artifactId>
	<version>1.1.0</version>
	<type>pom</type>
</dependency>
```

### gradle
```groovy
allprojects {
    repositories {
        maven { url 'https://dl.bintray.com/duoluo9/android' }
    }
}

implementation 'com.zhangteng.updateversion:updateversionlibrary:1.1.0'
```



###  ivy
```xml
<dependency org="com.zhangteng.updateversion" name="updateversionlibrary" rev="1.1.0">
	<artifact name="updateversionlibrary" ext="pom"></artifact>
</dependency>
```
## 效果图
![无wifi](https://img-blog.csdnimg.cn/20200807172122393.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2R1b2x1bzk=,size_16,color_FFFFFF,t_70)
![新版本](https://img-blog.csdnimg.cn/20200807172122399.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2R1b2x1bzk=,size_16,color_FFFFFF,t_70)

## 属性
属性名| 描述
--- | -----
isAutoInstall| 是否自动安装
isUpdateDialogShow| 是否显示更新提示
isProgressDialogShow| 是否显示下载进度
isHintVersion| 是否提示“当前已是最新版”
isUpdateTest| 是否是测试模式（数据来源：versionInfo.json）
isUpdateDownloadWithBrowser| 是否使用浏览器下载
isNotificationShow| 是否通知栏显示
checkUpdateCommonUrl| 获取版本信息url
provider| FileProvider(默认：BuildConfig.LIBRARY_PACKAGE_NAME + ".FileProvider")
themeColor| 主题色
uploadImage| 提示更新背景图
noNetImage| 无网络提示图
## 使用
```java
new UpdateVersion.Builder()
                //是否为调试模式
                .isUpdateTest(true)
                //通知栏显示
                .isNotificationShow(true)
                //是否自动安装
                .isAutoInstall(true)
                //获取服务器的版本信息
                .isCheckUpdateCommonUrl("http://")
                //是否提示更新信息
                .isHintVersion(true)
                //是否显示更新dialog
                .isUpdateDialogShow(true)
                //是否使用浏览器更新
                .isUpdateDownloadWithBrowser(false)
                .setProvider(BuildConfig.APPLICATION_ID + ".FileProvider")
                .build()
                //执行更新任务
                .updateVersion(new CommonHttpClient(this, this.getSupportFragmentManager()));
```

## 混淆
-keep public class com.zhangteng.**.*{ *; }
## 历史版本
版本| 更新| 更新时间
-------- | ----- | -----
v1.1.0| 迁移到androidx|2020/7/22 0022 at 下午 12:04
v1.0.2| 增加主题自定义| 2020/6/1 0001 at 下午 17:54

## 赞赏
如果您喜欢UpdateVersion，或感觉UpdateVersion帮助到了您，可以点右上角“Star”支持一下，您的支持就是我的动力，谢谢。您也可以扫描下面的二维码，请作者喝杯茶 tea

![支付宝收款码](https://img-blog.csdnimg.cn/20200807160902219.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2R1b2x1bzk=,size_16,color_FFFFFF,t_70)
![微信收款码](https://img-blog.csdnimg.cn/20200807160902112.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2R1b2x1bzk=,size_16,color_FFFFFF,t_70)

## 联系我
邮箱：763263311@qq.com/ztxiaoran@foxmail.com

## License
Copyright (c) [2020] [Swing]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
