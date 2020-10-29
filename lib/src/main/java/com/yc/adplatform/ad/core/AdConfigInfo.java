package com.yc.adplatform.ad.core;

public class AdConfigInfo {
    private String appId;
    private String appName;
    private String videoVertical;
    private String videoRewardHorizontal;
    private String videoReward;
    private String videoHorizontal;
    private String splash;
    private String inster;

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

    public String getVideoVertical() {
        return videoVertical;
    }

    public void setVideoVertical(String videoVertical) {
        this.videoVertical = videoVertical;
    }

    public String getVideoRewardHorizontal() {
        return videoRewardHorizontal;
    }

    public void setVideoRewardHorizontal(String videoRewardHorizontal) {
        this.videoRewardHorizontal = videoRewardHorizontal;
    }

    public String getVideoReward() {
        return videoReward;
    }

    public void setVideoReward(String videoReward) {
        this.videoReward = videoReward;
    }

    public String getVideoHorizontal() {
        return videoHorizontal;
    }

    public void setVideoHorizontal(String videoHorizontal) {
        this.videoHorizontal = videoHorizontal;
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


    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }
}
