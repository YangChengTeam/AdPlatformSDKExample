# "小游戏SDK” 对接文档

- 相关配置
  - 导入aar open_ad_sdk.aar 
  - 项目build.gradle  implementation 'com.yc.adplatformsdk:aar:1.0.2'  
  - AndroidManifest.xml 权限配置  [查看](https://github.com/YangChengTeam/AdPlatformSDKExample/blob/master/app/src/main/AndroidManifest.xml)

- 初始化
   -  生成对象 
      - AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
   -  初始化 
      - adPlatformSDK.init()
   -  调用广告
      - 开屏 
         - adPlatformSDK.showSplashAd()
      - 激励视频  
         - adPlatformSDK.showRewardAd()
      - 插屏 
         - adPlatformSDK.showInsertAd()
