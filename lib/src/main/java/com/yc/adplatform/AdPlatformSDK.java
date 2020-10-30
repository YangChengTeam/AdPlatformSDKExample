package com.yc.adplatform;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.FrameLayout;

import com.tencent.mmkv.MMKV;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdConfigInfo;
import com.yc.adplatform.ad.core.AdError;
import com.yc.adplatform.ad.core.AdType;
import com.yc.adplatform.ad.core.InitAdCallback;
import com.yc.adplatform.ad.core.SAdSDK;
import com.yc.adplatform.ad.ttad.STtAdSDk;
import com.yc.adplatform.business.InitEngin;
import com.yc.adplatform.business.InitInfo;
import com.yc.adplatform.log.AdLog;
import com.yc.adplatform.securityhttp.domain.GoagalInfo;
import com.yc.adplatform.securityhttp.domain.ResultInfo;
import com.yc.adplatform.securityhttp.net.contains.HttpConfig;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

public class AdPlatformSDK {

    public static AdPlatformSDK sInstance;

    public static AdPlatformSDK getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AdPlatformSDK.class) {
                if (sInstance == null) {
                    sInstance = new AdPlatformSDK(context);
                }
            }
        }
        return sInstance;
    }

    private AdPlatformSDK(Context context) {
        MMKV.initialize(context);

        GoagalInfo.get().init(context);
        HttpConfig.setPublickey("-----BEGIN PUBLIC KEY-----\n" +
                "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAq1wNiX3iQt+Q7juXZDNR\n" +
                "Eq2jGqx+2pXM4ddoZ1rkHb3XJFhrBguI/R11IfmTioPlTnheJqYkJf0NGzzxW2t1\n" +
                "nDKwbjoZ+d7UMehCDV44+FQMtzhRAFjmcQIXn6AaL2bkJFzHvoTtYPqgqgT5V4L6\n" +
                "+DhLSuPSwIVAC1aw1+iUk3jbg3ETzERSS6LDHTRi2ng7rpKAeHKeJ2RtbrcetCxv\n" +
                "YF+6QabnJhZGtr6cvp9CtFv5bSc2JsCqbJbsDGM6OPAjQjtpmImxQiXcI1gko8WP\n" +
                "+k1nx9GPJBhtdAXORRGRoHA8fUCveAJPDw1jSF3lBDf+1BHx+XeVX4/sVybd5Rn3\n" +
                "IE21UeuF+kbmwULJKUDzQNIwlXA+k4faRhdKeFCOeqldozwhP+575L/vVlyvxx/M\n" +
                "UJdA4vUziyO1l/IQEGzJ7b4AWfJ6sQEKDjODuLM+DM9MAuYddFnNfKj8XVi3jx9y\n" +
                "0OOAb/4Rb3UPeOUF9R4Sr0nLmL/1ITL8/9rJaue/e/D7H4xfQNbCtSTPhsa/+UOt\n" +
                "j3AQsNUjqkoLMXm7vtXEIshXEm4mlmMl98LsXyK3B6lMiV7jO4Vyp8muga8I/nH3\n" +
                "Snw5e86AHSZdnbQcLTDx9sgqN2mSL3MqLp9oiL4KGxNdNdt8EunGRycTsj09o7oz\n" +
                "Lfxf+/8xTiWygyUTThX+/GUCAwEAAQ==\n" +
                "-----END PUBLIC KEY-----");

        String agent_id = "1";
        Map<String, String> params = new HashMap<>();
        if (GoagalInfo.get().channelInfo != null && GoagalInfo.get().channelInfo.agent_id != null) {
            agent_id = GoagalInfo.get().channelInfo.agent_id;
        }

        params.put("agent_id", agent_id);
        params.put("device_type", "android");
        params.put("imeil", GoagalInfo.get().uuid);
        params.put("ts", System.currentTimeMillis() + "");
        String sv = android.os.Build.MODEL.contains(android.os.Build.BRAND) ? android.os.Build.MODEL + " " + android
                .os.Build.VERSION.RELEASE : Build.BRAND + " " + android
                .os.Build.MODEL + " " + android.os.Build.VERSION.RELEASE;
        params.put("sv", sv);
        if (GoagalInfo.get().getPackageInfo(context) != null) {
            params.put("version_code", GoagalInfo.get().getPackageInfo(context).versionCode + "");
            params.put("version_num", GoagalInfo.get().getPackageInfo(context).versionName + "");
        }
        HttpConfig.setDefaultParams(params);
    }


    private InitInfo mInitInfo;
    private String mAppId;

    public interface InitCallback {
        void onSuccess();

        void onFailure();
    }

    public void init(final Context context, String appId, final InitCallback initCallback) {
        this.mAppId = appId;
        new InitEngin(context).getInItInfo().subscribe(new Action1<ResultInfo<InitInfo>>() {
            @Override
            public void call(ResultInfo<InitInfo> initInfoResultInfo) {
                if (initInfoResultInfo != null && initInfoResultInfo.getCode() == 1 && initInfoResultInfo.getData() != null) {
                    AdPlatformSDK.this.mInitInfo = initInfoResultInfo.getData();
                    if (initCallback != null) {
                        AdConfigInfo adConfigInfo = mInitInfo.getAdConfigInfo();
                        SAdSDK.getImpl().initAd(context, adConfigInfo, new InitAdCallback() {
                            @Override
                            public void onSuccess() {
                                SAdSDK.getImpl().setAdConfigInfo(mInitInfo.getAdConfigInfo());
                                initCallback.onSuccess();
                            }

                            @Override
                            public void onFailure(AdError adError) {
                                Log.d("00671 securityhttp ", "initAd onFailure: " + adError.getMessage());
                                if (initCallback != null) {
                                    initCallback.onFailure();
                                }
                            }
                        });

                    }
                    return;
                }

                if (initCallback != null) {
                    initCallback.onFailure();
                }
            }
        });
    }

    private void sendClickLog(String adPosition, String adCode) {
        AdLog.sendLog(mInitInfo.getIp(), 12345, mAppId, "0", adPosition, adCode, "click");
    }

    private void sendShowLog(String adPosition, String adCode) {
        AdLog.sendLog(mInitInfo.getIp(), 12345, mAppId, "0", adPosition, adCode, "show");
    }

    private void showAd(Context context, AdType adType, String adPosition, String adCode, AdCallback callback, FrameLayout containerView) {
        STtAdSDk.getImpl().showAd(context, adType, new AdCallback() {
            @Override
            public void onDismissed() {
                if (callback != null) {
                    callback.onDismissed();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                if (callback != null) {
                    callback.onNoAd(adError);
                }
            }

            @Override
            public void onComplete() {
                if (callback != null) {
                    callback.onComplete();
                }
            }

            @Override
            public void onPresent() {
                if (callback != null) {
                    callback.onPresent();
                }
                sendShowLog(adPosition, adCode);
            }

            @Override
            public void onClick() {
                if (callback != null) {
                    callback.onClick();
                }
                sendClickLog(adPosition, adCode);
            }
        }, containerView);
    }

    private void showAd(Context context, AdType adType, String adPosition, String adCode, AdCallback callback) {
        showAd(context, adType, adPosition, adCode, callback, null);
    }

    public void showSplashAd(Context context, AdCallback callback, FrameLayout containerView) {
        String adCode = mInitInfo.getAdConfigInfo().getSplash();
        String adPosition = "ad_splash";
        showAd(context, AdType.SPLASH, adPosition, adCode, callback, containerView);
    }

    public void showInsertAd(Context context, AdCallback callback) {
        String adCode = mInitInfo.getAdConfigInfo().getInster();
        String adPosition = "ad_insert";
        showAd(context, AdType.INSERT, adPosition, adCode, callback);
    }
    
    public void showExpressAd(Context context, AdCallback callback) {
        String adCode = mInitInfo.getAdConfigInfo().getExpress();
        String adPosition = "ad_express";
        showAd(context, AdType.EXPRESS, adPosition, adCode, callback);
    }

    public void showFullScreenVideoVerticalAd(Context context, AdCallback callback) {
        String adCode = mInitInfo.getAdConfigInfo().getFullScreenVideoVertical();
        String adPosition = "ad_full_screen_video";
        showAd(context, AdType.FULL_SCREEN_VIDEO_VERTICAL, adPosition, adCode, callback);
    }

    public void showFullScreenVideoHorizontalAd(Context context, AdCallback callback) {
        String adCode = mInitInfo.getAdConfigInfo().getFullScreenVideoHorizontal();
        String adPosition = "ad_full_screen_video";
        showAd(context, AdType.FULL_SCREEN_VIDEO_HORIZON, adPosition, adCode, callback);
    }

    public void showRewardVideoVerticalAd(Context context, AdCallback callback) {
        String adCode = mInitInfo.getAdConfigInfo().getRewardVideoVertical();
        String adPosition = "ad_rewad_video";
        showAd(context, AdType.REWARD_VIDEO_VERTICAL, adPosition, adCode, callback);
    }

    public void showRewardVideoHorizontalAd(Context context, AdCallback callback) {
        String adCode = mInitInfo.getAdConfigInfo().getRewardVideoHorizontal();
        String adPosition = "ad_rewad_video";
        showAd(context, AdType.REWARD_VIDEO_HORIZON, adPosition, adCode, callback);
    }


}
