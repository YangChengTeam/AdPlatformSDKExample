package com.yc.adplatform.ad.core;

public class AdConfigInfo {
    private String appId;
    private String appName;

    private String splash;
    private String inster;
    private String express;

    private String rewardVideoHorizontal;
    private String rewardVideoVertical;
    private String fullScreenVideoVertical;
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

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }
}
