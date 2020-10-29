package com.yc.adplatformsdkexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.securityhttp.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);

        adPlatformSDK.init(this, "1", new AdPlatformSDK.InitCallback() {
            @Override
            public void onSuccess() {
                LogUtil.msg("----onSuccess");
                adPlatformSDK.showInsertAd(MainActivity.this, null);
            }

            @Override
            public void onFailure() {
                LogUtil.msg("----onFailure");
            }
        });

    }
}