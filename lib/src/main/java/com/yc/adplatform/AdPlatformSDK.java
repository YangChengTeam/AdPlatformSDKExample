package com.yc.adplatform;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
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
import com.yc.adplatform.securityhttp.utils.LogUtil;

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
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnEN9PULzGdCDba7vcLUOq2gbWpl1HvEOaiTKzLjHx46JoqwvHB5Pxb5ICirmyp66WLysHsDi7D0as4426iN9Y5NqCKAbT5RbAqbLYfBMNO56MoN78av/mu3XO8shxUThFQcpapr/e65PwLHgoQjdb3KpY2U3W0I0gzFKdaES5Qcc3sp43V4jCiyGPm9cxpEh0hr4+onMFB16M7Ai0K/V/HPnlR/ufYw7eG/qAiO8+FCn0EdbCd7y0EEB3pXG98xKf21shIM7Ikergd/06oLKvKB2e6Y4u3N7MVDDN/Vm+75iwIUSQYdaFwjhRzkFiPknSoOnitCxKrsDiXYmWCLrAQIDAQAB\n" +
                "-----END PUBLIC KEY-----");

        String agent_id = "1";
        Map<String, String> params = new HashMap<>();
        if (GoagalInfo.get().channelInfo != null && GoagalInfo.get().channelInfo.agent_id != null) {
            agent_id = GoagalInfo.get().channelInfo.agent_id;
        }

        params.put("agent_id", agent_id);
        params.put("device_type", "android");
        params.put("imei", GoagalInfo.get().uuid);
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

    public void setAdConfigInfo(AdConfigInfo adConfigInfo) {
        if(this.mInitInfo == null) {
            this.mInitInfo = new InitInfo();
        }
        this.mInitInfo.setAdConfigInfo(adConfigInfo);
    }

    public interface InitCallback {
        void onSuccess();  // 初始化成功

        void onFailure(); // 初始化失嵊

        void onAdInitSuccess(); // 广告初始化成功

        void onAdInitFailure(); // 广告初始化失嵊
    }

    private int initCount = 3;
    public void init(final Context context, String appId, final InitCallback initCallback) {

        this.mInitInfo = new InitInfo();
        this.mAppId = appId;

        final boolean[] isInitSuccess = {false};

        new InitEngin(context).getInItInfo(appId).subscribe(new Action1<ResultInfo<InitInfo>>() {
            @Override
            public void call(ResultInfo<InitInfo> initInfoResultInfo) {
                if (initInfoResultInfo != null && initInfoResultInfo.getCode() == 1 && initInfoResultInfo.getData() != null) {
                    if (initCallback != null) {
                        initCallback.onSuccess();
                        isInitSuccess[0] = true;
                        LogUtil.msg("init: 初始化成功");
                    }
                }

                if (!isInitSuccess[0] && --initCount > 0) {
                    init(context, appId, initCallback);
                    LogUtil.msg("reinit: 初始化次数" + initCount);
                    return;
                }

                if (!isInitSuccess[0] && initCallback != null) {
                    initCallback.onFailure();
                    LogUtil.msg("init: 初始化失败");
                }

                InitInfo initInfo = initInfoResultInfo.getData();
                AdConfigInfo adConfigInfo = initInfo.getAdConfigInfo();
                if(adConfigInfo == null || TextUtils.isEmpty(adConfigInfo.getAppId())){
                    initInfo.setAdConfigInfo(mInitInfo.getAdConfigInfo());
                }
                mInitInfo = initInfo;

                if(adConfigInfo == null || TextUtils.isEmpty(adConfigInfo.getAppId())){
                    initCallback.onAdInitFailure();
                    LogUtil.msg("adinit: 广告初始化失败 未配置广告信息");
                    return;
                }

                SAdSDK.getImpl().initAd(context, adConfigInfo, new InitAdCallback() {
                    @Override
                    public void onSuccess() {
                        SAdSDK.getImpl().setAdConfigInfo(mInitInfo.getAdConfigInfo());
                        if (initCallback != null) {
                            initCallback.onAdInitSuccess();
                        }
                        LogUtil.msg("adinit: 广告初始化成功");
                    }

                    @Override
                    public void onFailure(AdError adError) {
                        if (initCallback != null) {
                            initCallback.onAdInitFailure();
                        }
                        LogUtil.msg("adinit: 广告初始化失败 " + adError.getMessage());
                    }
                });

            }
        });
    }

    private void sendClickLog(String adPosition, String adCode) {
        if (mInitInfo == null) return;
        AdLog.sendLog(mInitInfo.getIp(), 41234, mAppId, "0", adPosition, adCode, "click");
    }

    private void sendShowLog(String adPosition, String adCode) {
        if (mInitInfo == null) return;
        AdLog.sendLog(mInitInfo.getIp(), 41234, mAppId, "0", adPosition, adCode, "show");
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

    public void showBannerAd(Context context, AdCallback callback, FrameLayout containerView) {
        String adCode = mInitInfo.getAdConfigInfo().getBanner();
        String adPosition = "ad_banner";
        showAd(context, AdType.BANNER, adPosition, adCode, callback, containerView);
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
