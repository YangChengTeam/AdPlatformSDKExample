package com.yc.adplatform.ad.ttad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdConfigInfo;
import com.yc.adplatform.ad.core.AdError;
import com.yc.adplatform.ad.core.AdType;
import com.yc.adplatform.ad.core.ISGameSDK;
import com.yc.adplatform.ad.core.InitAdCallback;

import java.lang.ref.WeakReference;
import java.util.List;


public class STtAdSDk implements ISGameSDK {

    private static final String TAG = "00671 securityhttp";

    private static STtAdSDk sTtAdSDk;
    WeakReference<ViewGroup> mSplashContainer;

    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;
    private static final int MSG_GO_MAIN = 1;

    //开屏广告是否已经加载
    //开屏广告加载发生超时但是SDK没有及时回调结果的时候，做的一层保护。
    private boolean mHasLoaded;

    private WeakReference<Context> mContext;

    protected AdConfigInfo mAdConfigInfo;

    public void setAdConfigInfo(AdConfigInfo adConfigInfo) {
        this.mAdConfigInfo = adConfigInfo;
    }

    public static STtAdSDk getImpl() {
        if (sTtAdSDk == null) {
            synchronized (STtAdSDk.class) {
                if (sTtAdSDk == null) {
                    sTtAdSDk = new STtAdSDk();
                }
            }
        }
        return sTtAdSDk;
    }

    private int insertWidth = 300;
    private int insertHeight = 450;

    public void setInsertSize(int width, int height){
        this.insertWidth = width;
        this.insertHeight = height;
    }


    private int bannerWidth = 500;
    private int bannerHeight = 200;

    public void setBannerSize(int width, int height){
        this.bannerWidth = width;
        this.bannerHeight = height;
    }

    private void loadBannerAd(String bannerId, AdCallback adCallback) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(bannerId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(bannerWidth, bannerHeight) //期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(640, 320)//这个参数设置即可，不影响个性化模板广告的size
                .build();

        TTAdManagerHolder.get().createAdNative(mContext.get()).loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                if (mSplashContainer != null) {
                    mSplashContainer.get().removeAllViews();
                }
                AdError adError = new AdError();
                adError.setMessage(message);
                adError.setCode(String.valueOf(code));
                if (adCallback != null) {
                    adCallback.onNoAd(adError);
                }
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);
                mTTAd.setSlideIntervalTime(30 * 1000);//设置轮播间隔 ms,不调用则不进行轮播展示
                bindAdListenerExpress(mTTAd, adCallback);
                mTTAd.render();//调用render开始渲染广告
            }
        });
    }

    private void loadExpressAd(String feedId, AdCallback adCallback) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(feedId)
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(254, 200)
                .build();

        TTAdManagerHolder.get().createAdNative(mContext.get()).loadNativeExpressAd(adSlot,
                new TTAdNative.NativeExpressAdListener() {
                    @Override
                    public void onError(int code, String message) {
                        if (mSplashContainer != null) {
                            mSplashContainer.get().removeAllViews();
                        }
                        AdError adError = new AdError();
                        adError.setMessage(message);
                        adError.setCode(String.valueOf(code));
                        if (adCallback != null) {
                            adCallback.onNoAd(adError);
                        }
                    }

                    @Override
                    public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                        if (ads == null || ads.size() == 0) {
                            return;
                        }
                        TTNativeExpressAd mTTAd = ads.get(0);
                        bindAdListenerExpress(mTTAd, adCallback);
                        startTime = System.currentTimeMillis();
                        mTTAd.render();
                    }
                });
    }


    private void bindAdListenerExpress(TTNativeExpressAd ad, AdCallback callback) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {

            @Override
            public void onAdClicked(View view, int type) {
                Log.e(TAG, "广告被点击");
                if (callback != null)
                    callback.onClick();
            }

            @Override
            public void onAdShow(View view, int type) {
                Log.e(TAG, "广告展示");
                if (callback != null)
                    callback.onPresent();
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e(TAG, "渲染失败 render fail:" + (System.currentTimeMillis() - startTime));
                AdError adError = new AdError();
                adError.setMessage("渲染失败 " + msg);
                adError.setCode(String.valueOf(code));
                if (callback != null)
                    callback.onNoAd(adError);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e(TAG, "渲染成功 render suc:" + (System.currentTimeMillis() - startTime));
                if (mSplashContainer != null) {
                    mSplashContainer.get().removeAllViews();
                    mSplashContainer.get().addView(view);
                }
            }
        });
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
//                TToast.show(mContext.get(), "点击开始下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    Log.d(TAG, "onDownloadActive: " + "下载中，点击暂停");
//                    TToast.show(mContext.get(), "下载中，点击暂停", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                Log.d(TAG, "onDownloadPaused: " + "下载暂停，点击继续");
//                TToast.show(mContext.get(), "下载暂停，点击继续", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                TToast.show(mContext.get(), "下载失败，点击重新下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onInstalled(String fileName, String appName) {
//                TToast.show(mContext.get(), "安装完成，点击图片打开", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                TToast.show(mContext.get(), "点击安装", Toast.LENGTH_LONG);
            }
        });
    }

    private void bindDislike(TTNativeExpressAd ad, boolean customStyle) {
        //使用默认模板中默认dislike弹出样式
        ad.setDislikeCallback((Activity) mContext.get(), new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                //TToast.show(mContext, "反馈了 " + value);
                //用户选择不喜欢原因后，移除广告展示
                if (mSplashContainer != null) {
                    mSplashContainer.get().removeAllViews();
                }
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: 点击取消");
            }

            @Override
            public void onRefuse() {
                Log.d(TAG, "onRefuse: 您已成功提交反馈，请勿重复提交哦！ ");
            }

        });
    }

    /**
     * 加载插屏广告
     */
    private void loadInteractionAd(String codeId, AdCallback callback) {
        //step4:创建插屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(this.insertWidth, this.insertHeight) //根据广告平台选择的尺寸，传入同比例尺寸
                .build();
        //step5:请求广告，调用插屏广告异步请求接口
        TTAdManagerHolder.get().createAdNative(mContext.get()).loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                AdError adError = new AdError();
                adError.setMessage(message);
                adError.setCode(String.valueOf(code));
                if (callback != null)
                    callback.onNoAd(adError);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                TTNativeExpressAd ttNativeExpressAd = ads.get(0);
                bindAdListener(ttNativeExpressAd, callback);
                bindDislike(ttNativeExpressAd, false);
                startTime = System.currentTimeMillis();
                ttNativeExpressAd.render();
            }
        });
    }

    private void bindAdListener(TTNativeExpressAd ad, AdCallback callback) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdDismiss() {
                Log.e(TAG, "广告关闭");
                if (callback != null)
                    callback.onDismissed();
            }

            @Override
            public void onAdClicked(View view, int type) {
                Log.e(TAG, "广告被点击");
                if (callback != null)
                    callback.onClick();
            }

            @Override
            public void onAdShow(View view, int type) {
                Log.e(TAG, "广告展示");
                if (callback != null)
                    callback.onPresent();
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e(TAG, "渲染失败 render fail:" + (System.currentTimeMillis() - startTime));
                AdError adError = new AdError();
                adError.setMessage("渲染失败 " + msg);
                adError.setCode(String.valueOf(code));
                if (callback != null)
                    callback.onNoAd(adError);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e(TAG, "渲染成功 render suc:" + (System.currentTimeMillis() - startTime));
                //返回view的宽高 单位 dp
                ad.showInteractionExpressAd((Activity) (mContext.get()));

            }
        });
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
//                TToast.show(mContext.get(), "点击开始下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    Log.d(TAG, "onDownloadActive: " + "下载中，点击暂停");
//                    TToast.show(mContext.get(), "下载中，点击暂停", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                Log.d(TAG, "onDownloadPaused: " + "下载暂停，点击继续");
//                TToast.show(mContext.get(), "下载暂停，点击继续", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                TToast.show(mContext.get(), "下载失败，点击重新下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onInstalled(String fileName, String appName) {
//                TToast.show(mContext.get(), "安装完成，点击图片打开", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                TToast.show(mContext.get(), "点击安装", Toast.LENGTH_LONG);
            }
        });
    }




    //    private TTNativeExpressAd mTTAd;
    private long startTime = 0;
    private boolean mHasShowDownloadActive = false;

    @Override
    public void initAd(Context context, AdConfigInfo adConfigInfo, InitAdCallback initAdCallback) {
        mContext = new WeakReference<>(context);
        this.mAdConfigInfo = adConfigInfo;
        if (adConfigInfo == null) return;
        TTAdManagerHolder.init(mContext.get(), mAdConfigInfo.getAppId(), mAdConfigInfo.getAppName(), mAdConfigInfo.isDebug(), initAdCallback);
    }

    @Override
    public void initUser(Context context, InitAdCallback initAdCallback) {

    }

    @Override
    public void showAd(Context context, AdType type, AdCallback callback) {
        this.showAd(context, type, callback, null);
    }

    @Override
    public void showAd(Context context, AdType type, AdCallback callback, ViewGroup viewGroup) {
        mContext = new WeakReference<>(context);
        if (mAdConfigInfo == null) {
            if (callback != null) {
                AdError error = new AdError();
                error.setMessage("AdConfigInfo is null");
                error.setCode("500");
                callback.onNoAd(error);
            }
            return;
        }


        switch (type) {
            case BANNER:
                mSplashContainer = new WeakReference<>(viewGroup);
                loadBannerAd(mAdConfigInfo.getBanner(), callback);
                break;
            case EXPRESS:
                mSplashContainer = new WeakReference<>(viewGroup);
                loadExpressAd(mAdConfigInfo.getExpress(), callback);
                break;
            case INSERT:
                loadInteractionAd(mAdConfigInfo.getInster(), callback);
                break;
            case REWARD_VIDEO_VERTICAL:
                loadRewardVideoAd(mAdConfigInfo.getRewardVideoVertical(), TTAdConstant.VERTICAL, callback);
                break;
            case REWARD_VIDEO_HORIZON:
                loadRewardVideoAd(mAdConfigInfo.getRewardVideoHorizontal(), TTAdConstant.HORIZONTAL, callback);
                break;
            case FULL_SCREEN_VIDEO_VERTICAL:
                loadVideoAd(mAdConfigInfo.getFullScreenVideoVertical(), TTAdConstant.VERTICAL, callback);
                break;
            case FULL_SCREEN_VIDEO_HORIZON:
                loadVideoAd(mAdConfigInfo.getRewardVideoHorizontal(), TTAdConstant.HORIZONTAL, callback);
                break;
            case SPLASH:
                //定时，AD_TIME_OUT时间到时执行，如果开屏广告没有加载则跳转到主页面
                mSplashContainer = new WeakReference<>(viewGroup);
                loadSplashAd(mAdConfigInfo.getSplash(), callback);  // 加载开屏广告
                break;
        }
    }


    private TTRewardVideoAd mttRewardVideoAd;

    /**
     * 加载激励视频广告
     */
    private void loadRewardVideoAd(String codeId, int orientation, AdCallback callback) {
        //个性化模板广告需要传入期望广告view的宽、高，单位dp，
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(3)  //奖励的数量
                //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                .setExpressViewAcceptedSize(500, 500)
                .setUserID("")//用户id,必传参数
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();

        TTAdManagerHolder.get().createAdNative(mContext.get()).loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "onError: loadRewardVideoAd onError code " + code + "  message " + message);
                AdError adError = new AdError();
                adError.setTripartiteCode(code);
                adError.setMessage(message);
                if (callback != null)
                    callback.onNoAd(adError);
            }

            //视频广告加载后的视频文件资源缓存到本地的回调
            @Override
            public void onRewardVideoCached() {
                Log.d(TAG, "onRewardVideoCached: loadRewardVideoAd 视频广告加载后的视频文件资源缓存到本地的回调");
            }

            //视频广告素材加载到，如title,视频url等，不包括视频文件
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.d(TAG, "onRewardVideoAdLoad: 激励视频广告 rewardVideoAd loaded 加载完成");
                mttRewardVideoAd = ad;
                //mttRewardVideoAd.setShowDownLoadBar(false);
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "onAdShow: loadRewardVideoAd");
                        if (callback != null)
                            callback.onPresent();
                    }

                    @Override
                    public void onAdVideoBarClick() { ////广告的下载bar点击回调
                        Log.d(TAG, "onAdVideoBarClick: loadRewardVideoAd 广告的下载bar点击回调");
                        if (callback != null)
                            callback.onClick();
                    }

                    @Override
                    public void onAdClose() { //视频广告关闭回调
                        Log.d(TAG, "onAdClose: loadRewardVideoAd 视频广告关闭回调");
                        if (callback != null)
                            callback.onDismissed();
                    }

                    @Override
                    public void onVideoComplete() {   //视频广告播放完毕回调
                        Log.d(TAG, "onVideoComplete: loadRewardVideoAd 视频广告播放完毕回调");
                    }

                    @Override
                    public void onVideoError() {
                        Log.d(TAG, "onVideoError: loadRewardVideoAd");
                        AdError adError = new AdError();
                        adError.setMessage("RewardAdInteractionListener_onVideoError");
                        if (callback != null)
                            callback.onNoAd(adError);
                    }

                    @Override
                    public void onRewardVerify(boolean b, int i, String s, int i1, String s1) {
                        Log.d(TAG, "onRewardVerify: loadRewardVideoAd  视频广告播完验证奖励有效性回调，参数分别为是否有效，奖励数量，奖励名称  ");
                        if (callback != null)
                            callback.onComplete();
                    }

                    @Override
                    public void onSkippedVideo() {  //跳过
                        Log.d(TAG, "onSkippedVideo: loadRewardVideoAd 跳过 ");
                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {

                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {

                    }
                });
                if (mttRewardVideoAd != null) {
                    //step6:在获取到广告后展示
                    mttRewardVideoAd.showRewardVideoAd((Activity) mContext.get());
                    mttRewardVideoAd = null;
                }
            }
        });
    }

    private TTFullScreenVideoAd mttFullVideoAd;

    /**
     * 加载视频广告
     */
    @SuppressWarnings("SameParameterValue")
    private void loadVideoAd(String codeId, int orientation, AdCallback callback) {
        Log.d(TAG, "loadVideoAd: 加载视频广告 codeId " + codeId);
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setOrientation(orientation)//必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        //step5:请求广告
        TTAdManagerHolder.get().createAdNative(mContext.get()).loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                AdError adError = new AdError();
                adError.setTripartiteCode(code);
                adError.setMessage(message);
                if (callback != null)
                    callback.onNoAd(adError);
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
                mttFullVideoAd = ad;
                mttFullVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        if (callback != null)
                            callback.onPresent();
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        if (callback != null)
                            callback.onClick();
                    }

                    @Override
                    public void onAdClose() {
                        if (callback != null)
                            callback.onDismissed();
                    }

                    @Override
                    public void onVideoComplete() {
                        if (callback != null)
                            callback.onComplete();
                    }

                    @Override
                    public void onSkippedVideo() {
                    }

                });
                if (mttFullVideoAd != null) {
                    //step6:在获取到广告后展示
                    mttFullVideoAd.showFullScreenVideoAd((Activity) mContext.get());
                    mttFullVideoAd = null;
                }
            }

            @Override
            public void onFullScreenVideoCached() {
            }
        });
    }


    /**
     * 加载开屏广告
     */
    private void loadSplashAd(String codeId, AdCallback callback) {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codeId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .build();
        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        TTAdManagerHolder.get().createAdNative(mContext.get()).loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d(TAG, message);
                mHasLoaded = true;
                AdError adError = new AdError();
                adError.setMessage(message);
                adError.setCode(String.valueOf(AdError.AD_ERROR));
                adError.setTripartiteCode(code);
                if (callback != null)
                    callback.onNoAd(adError);
            }

            @Override
            public void onTimeout() {
                mHasLoaded = true;
                AdError adError = new AdError();
                adError.setCode(String.valueOf(AdError.AD_ERROR));
                if (callback != null)
                    callback.onNoAd(adError);
            }

            @Override
            public void onSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");
                mHasLoaded = true;
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();

                ViewGroup viewGroup;
                if (mSplashContainer != null) {
                    viewGroup = mSplashContainer.get();
                } else {
                    Activity activity = (Activity) mContext.get();
                    viewGroup = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                }

                if (viewGroup != null) {
                    viewGroup.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕宽
                    viewGroup.addView(view);
                }
                //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                //ad.setNotAllowSdkCountdown();

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                        if (callback != null)
                            callback.onClick();
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                        if (callback != null)
                            callback.onPresent();
                    }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip" + callback);
                        if (callback != null) {
                            callback.onDismissed();
//                            mSplashContainer.removeAllViews();
//                            mSplashContainer = null;
                        }
                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                        if (callback != null) {
                            callback.onDismissed();
//                            mSplashContainer.removeAllViews();
//                            mSplashContainer = null;
                        }
                    }
                });
            }
        }, AD_TIME_OUT);
    }
}
