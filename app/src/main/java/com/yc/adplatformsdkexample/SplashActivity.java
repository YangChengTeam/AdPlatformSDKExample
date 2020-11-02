package com.yc.adplatformsdkexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.whychl.TrickyCastle.R;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.adplatform.securityhttp.utils.VUiKit;

public class SplashActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private boolean isAdClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mFrameLayout = findViewById(R.id.fl_ad_container);

        VUiKit.postDelayed(2000, new Runnable() {
            @Override
            public void run() {
                showSplash();

            }
        });
    }


    private void showSplash() {
        AdPlatformSDK.getInstance(this).showSplashAd(this, new AdCallback() {
            @Override
            public void onDismissed() {
                Log.d("00671 securityhttp ", "showSplash onDismissed: ");
                startMainActivity(0);
            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("00671 securityhttp ", "showSplash  onNoAd: ");
                startMainActivity(0);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onPresent() {
                Log.d("00671 securityhttp ", "showSplash onPresent: ");
            }

            @Override
            public void onClick() {
                Log.d("00671 securityhttp ", "showSplash onClick: ");
                isAdClick = true;
            }
        }, mFrameLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAdClick) {
            isAdClick = false;
            startMainActivity(500);
        }
    }

    private void startMainActivity(long dealy) {
        VUiKit.postDelayed(dealy, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}