package com.yc.adplatform.business;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.yc.adplatform.ad.core.AdConfigInfo;

import java.util.ArrayList;
import java.util.List;

public class InitInfo {

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    private String ip;

    @JSONField(name = "ad_info")
    private AdConfigInfo adConfigInfo;
    public AdConfigInfo getAdConfigInfo() {
        if (adConfigInfo == null) {
            adConfigInfo = new AdConfigInfo();
            adConfigInfo.setAppId("5107713");
            adConfigInfo.setAppName("乐乐游戏_android");
            adConfigInfo.setVideoVertical("945501615");
            adConfigInfo.setVideoReward("945501615");
            adConfigInfo.setVideoRewardHorizontal("945501615");
            adConfigInfo.setVideoHorizontal("945501615");
            adConfigInfo.setVideoVertical("945501615");
            adConfigInfo.setSplash("887384131");
            adConfigInfo.setInster("945550532");
            adConfigInfo.setDebug(false);
        }
        return adConfigInfo;
    }

    public void setAdConfigInfo(AdConfigInfo adConfigInfo) {
        this.adConfigInfo = adConfigInfo;
    }
}
