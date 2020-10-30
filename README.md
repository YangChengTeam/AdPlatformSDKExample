# "小游戏SDK” 对接文档

- 配置
  - 导入aar open_ad_sdk.aar  [下载open_ad_sdk](https://github.com/YangChengTeam/AdPlatformSDKExample/blob/master/app/libs/open_ad_sdk.aar)
  - 根目录build.gradle maven配置  [查看](https://github.com/YangChengTeam/AdPlatformSDKExample/blob/master/build.gradle)
    -   maven {url 'https://raw.githubusercontent.com/YangChengTeam/AdPlatformSDKExample/master/lib/AAR'}

  - 项目build.gradle  implementation 'com.yc.adplatformsdk:aar:1.0.2'  
  - AndroidManifest.xml 权限配置  [查看](https://github.com/YangChengTeam/AdPlatformSDKExample/blob/master/app/src/main/AndroidManifest.xml)

- 代码
   -  生成对象 
      - AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
   -  初始化 
      - adPlatformSDK.init()
   -  调用广告
      - 开屏 
         - adPlatformSDK.showSplashAd()
      - 激励视频  
         - 竖屏
             - adPlatformSDK.showVideoRewardVerticalAd()
         - 横屏
             - adPlatformSDK.showVideoRewardHorizontalAd()

      - 全屏视频  
         - 竖屏
             - adPlatformSDK.showVideoVerticalAd()
        - 横屏
             - adPlatformSDK.showVideoHorizontalAd()
      - 插屏 
         - adPlatformSDK.showInsertAd()
