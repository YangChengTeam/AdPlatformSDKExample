package com.yc.adplatformsdkexample;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdConfigInfo;
import com.yc.adplatform.business.InitInfo;
import com.yc.adplatform.securityhttp.utils.LogUtil;
import com.yc.adplatform.securityhttp.utils.VUiKit;
import com.yc.adplatformsdkexample.hook.Hook;
import com.yc.uuid.UDID;
import com.yc.uuid.UDIDInfo;

import java.lang.reflect.InvocationTargetException;

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
        UDID.getInstance(this).init();




        app = this;

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
