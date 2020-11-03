package com.yc.adplatformsdkexample;

import android.app.Application;

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdConfigInfo;
import com.yc.adplatform.securityhttp.utils.LogUtil;

public class App extends Application {

    private static App app;

    public static App getApp() {
        return app;
    }


    private AdPlatformSDK.InitCallback initCallback;

    public void setInitCallback(AdPlatformSDK.InitCallback initCallback) {
        this.initCallback = initCallback;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

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
                if (initCallback != null) {
                    initCallback.onSuccess();
                }
            }

            @Override
            public void onFailure() {
                if (initCallback != null) {
                    initCallback.onFailure();
                }
            }

            @Override
            public void onAdInitSuccess() {
                if (initCallback != null) {
                    initCallback.onAdInitSuccess();
                }
            }

            @Override
            public void onAdInitFailure() {
                if (initCallback != null) {
                    initCallback.onAdInitFailure();
                }
            }
        });
    }
}
