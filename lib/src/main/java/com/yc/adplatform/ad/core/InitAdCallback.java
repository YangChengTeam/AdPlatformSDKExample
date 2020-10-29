package com.yc.adplatform.ad.core;

public interface InitAdCallback {
    void onSuccess();

    void onFailure(AdError adError);
}
