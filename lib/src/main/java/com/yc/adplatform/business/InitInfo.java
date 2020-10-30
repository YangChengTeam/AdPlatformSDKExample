package com.yc.adplatform.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.yc.adplatform.ad.core.AdConfigInfo;

public class InitInfo {

    public String getIp() {
        if(ip == null || ip.length() < 12){
            ip = "";
        }
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
            adConfigInfo.setFullScreenVideoVertical("945501615");
            adConfigInfo.setRewardVideoVertical("945501615");
            adConfigInfo.setRewardVideoHorizontal("945501615");
            adConfigInfo.setFullScreenVideoHorizontal("945501615");
            adConfigInfo.setFullScreenVideoVertical("945501615");
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
