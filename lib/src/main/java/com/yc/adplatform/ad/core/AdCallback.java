package com.yc.adplatform.ad.core;

public interface AdCallback {
    void onDismissed();

    void onNoAd(AdError adError);

    void onComplete();

    void onPresent();

    void onClick();
}
