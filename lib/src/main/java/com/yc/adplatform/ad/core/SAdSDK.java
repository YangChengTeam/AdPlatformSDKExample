package com.yc.adplatform.ad.core;

import android.content.Context;
import android.view.ViewGroup;

import com.yc.adplatform.ad.ttad.STtAdSDk;

public class SAdSDK implements ISGameSDK {
    private static SAdSDK sAdSDK;
    private String TAG = "00671 securityhttp";


    public static SAdSDK getImpl() {
        if (sAdSDK == null) {
            synchronized (SAdSDK.class) {
                if (sAdSDK == null) {
                    sAdSDK = new SAdSDK();
                }
            }
        }
        return sAdSDK;
    }

    public void setAdConfigInfo(AdConfigInfo adConfigInfo) {
        STtAdSDk.getImpl().setAdConfigInfo(adConfigInfo);
    }


    @Override
    public void initAd(Context context, AdConfigInfo adConfigInfo, InitAdCallback initAdCallback) {
        STtAdSDk.getImpl().initAd(context, adConfigInfo, initAdCallback);
    }

    @Override
    public void initUser(Context context, InitAdCallback initAdCallback) {
        STtAdSDk.getImpl().initUser(context, initAdCallback);
    }

    /**
     * @param context  上下文对象
     * @param type     广告类型
     * @param callback 广告状态
     */
    @Override
    public void showAd(Context context, AdType type, AdCallback callback) {
        this.showAd(context, type, callback, null);
    }

    @Override
    public void showAd(Context context, AdType type, AdCallback callback, ViewGroup viewGroup) {
        STtAdSDk.getImpl().showAd(context, type, callback, viewGroup);
    }

}
