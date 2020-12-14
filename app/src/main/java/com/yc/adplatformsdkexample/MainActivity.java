package com.yc.adplatformsdkexample;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.whychl.TrickyCastle.R;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.securityhttp.utils.VUiKit;
import com.yc.uuid.UDID;
import com.yc.uuid.UDIDInfo;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final UDIDInfo udidInfo = UDID.getInstance(this).build();
        VUiKit.postDelayed(300, new Runnable() {
            @Override
            public void run() {
                AdPlatformSDK.getInstance(getBaseContext()).init(getBaseContext(), "1", udidInfo, new AdPlatformSDK.InitCallback(){

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }



        });
        findViewById(R.id.insert_btn).setOnClickListener(this);
        findViewById(R.id.express_btn).setOnClickListener(this);
        findViewById(R.id.reward_video_btn).setOnClickListener(this);
        findViewById(R.id.full_video_btn).setOnClickListener(this);
        findViewById(R.id.banner_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);

    }


}