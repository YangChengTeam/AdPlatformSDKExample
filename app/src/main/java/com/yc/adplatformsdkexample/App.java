package com.yc.adplatformsdkexample;

import android.app.Application;

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdConfigInfo;
import com.yc.adplatform.securityhttp.utils.LogUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);

        AdConfigInfo adConfigInfo = new AdConfigInfo();
        adConfigInfo.setAppId("5108784");
        adConfigInfo.setAppName("还我宠物");
        adConfigInfo.setBanner("945573585");
        adConfigInfo.setInster("945568880");
        adConfigInfo.setRewardVideoHorizontal("945573588");
        adPlatformSDK.setAdConfigInfo(adConfigInfo);
        adPlatformSDK.init(this, "1", new AdPlatformSDK.InitCallback() {
            @Override
            public void onSuccess() {
                LogUtil.msg("----onSuccess");
            }

            @Override
            public void onFailure() {
                LogUtil.msg("----onFailure");
            }

            @Override
            public void onAdInitSuccess() {
                LogUtil.msg("----onAdInitSuccess");
            }

            @Override
            public void onAdInitFailure() {
                LogUtil.msg("----onAdInitFailure");
            }
        });
    }
}
