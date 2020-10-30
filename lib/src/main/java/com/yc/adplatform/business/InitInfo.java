package com.yc.adplatform.business;

import com.alibaba.fastjson.annotation.JSONField;
import com.yc.adplatform.ad.core.AdConfigInfo;

public class InitInfo {

    public String getIp() {
        if (ip == null || ip.length() < 12) {
            ip = "8.129.120.255";
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
        return adConfigInfo;
    }

    public void setAdConfigInfo(AdConfigInfo adConfigInfo) {
        this.adConfigInfo = adConfigInfo;
    }
}
