package com.yc.adplatform.ad.core;

import com.alibaba.fastjson.annotation.JSONField;

public class AdConfigInfo {
    @JSONField(name = "app_id")
    private String appId;

    @JSONField(name = "app_name")
    private String appName;

    private String splash;
    private String inster;
    private String express;
    private String banner;

    @JSONField(name = "reward_video_horizontal")
    private String rewardVideoHorizontal;

    @JSONField(name = "reward_video_vertical")
    private String rewardVideoVertical;

    @JSONField(name = "full_screen_video_vertical")
    private String fullScreenVideoVertical;

    @JSONField(name = "full_screen_video_horizontal")
    private String fullScreenVideoHorizontal;

    private boolean isDebug = true;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFullScreenVideoVertical() {
        return fullScreenVideoVertical;
    }

    public void setFullScreenVideoVertical(String fullScreenVideoVertical) {
        this.fullScreenVideoVertical = fullScreenVideoVertical;
    }

    public String getRewardVideoHorizontal() {
        return rewardVideoHorizontal;
    }

    public void setRewardVideoHorizontal(String rewardVideoHorizontal) {
        this.rewardVideoHorizontal = rewardVideoHorizontal;
    }

    public String getRewardVideoVertical() {
        return rewardVideoVertical;
    }

    public void setRewardVideoVertical(String rewardVideoVertical) {
        this.rewardVideoVertical = rewardVideoVertical;
    }

    public String getFullScreenVideoHorizontal() {
        return fullScreenVideoHorizontal;
    }

    public void setFullScreenVideoHorizontal(String fullScreenVideoHorizontal) {
        this.fullScreenVideoHorizontal = fullScreenVideoHorizontal;
    }

    public String getSplash() {
        return splash;
    }

    public void setSplash(String splash) {
        this.splash = splash;
    }

    public String getInster() {
        return inster;
    }

    public void setInster(String inster) {
        this.inster = inster;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }


}
