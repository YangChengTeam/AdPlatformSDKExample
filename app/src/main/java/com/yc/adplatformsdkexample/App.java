package com.yc.adplatformsdkexample;

import android.app.Application;
import android.widget.Toast;

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdConfigInfo;
import com.yc.adplatform.securityhttp.utils.LogUtil;
import com.yc.adplatform.securityhttp.utils.VUiKit;
import com.yc.adplatformsdkexample.hook.Hook;

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

        VUiKit.postDelayed(2000, new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.this, "ssss", Toast.LENGTH_LONG).show();
            }
        });

        app = this;

    }

}
