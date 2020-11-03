package com.yc.adplatformsdkexample;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.whychl.TrickyCastle.R;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.insert_btn).setOnClickListener(this);
        findViewById(R.id.express_btn).setOnClickListener(this);
        findViewById(R.id.reward_video_btn).setOnClickListener(this);
        findViewById(R.id.full_video_btn).setOnClickListener(this);
        findViewById(R.id.banner_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        if (view.getId() == R.id.insert_btn) {
            adPlatformSDK.showInsertAd(this,300, 200, this);
        } else if (view.getId() == R.id.express_btn) {
            adPlatformSDK.showExpressAd(this,this, (FrameLayout) findViewById(R.id.fl_ad_container));
        } else if (view.getId() == R.id.reward_video_btn) {
            adPlatformSDK.showRewardVideoHorizontalAd(this,this);
        } else if (view.getId() == R.id.full_video_btn) {
            adPlatformSDK.showFullScreenVideoHorizontalAd(this,this);
        } else if (view.getId() == R.id.banner_btn) {
            adPlatformSDK.showBannerAd(this,this,  (FrameLayout) findViewById(R.id.fl_ad_container));
        }
    }

    @Override
    public void onDismissed() {

    }

    @Override
    public void onNoAd(AdError adError) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onPresent() {

    }

    @Override
    public void onClick() {

    }
}