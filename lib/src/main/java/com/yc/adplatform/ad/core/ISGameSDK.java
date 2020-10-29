package com.yc.adplatform.ad.core;

import android.content.Context;
import android.view.ViewGroup;


public interface ISGameSDK {

    void initAd(Context context, AdConfigInfo mAdConfigInfo, InitAdCallback initAdCallback);

    void initUser(Context context, InitAdCallback initAdCallback);

    void showAd(Context context, AdType type, AdCallback callback);

    void showAd(Context context, AdType type, AdCallback callback, ViewGroup viewGroup);
}
