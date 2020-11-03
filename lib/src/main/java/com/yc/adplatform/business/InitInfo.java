package com.yc.adplatform.business;

import android.text.TextUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.yc.adplatform.ad.core.AdConfigInfo;

public class InitInfo {
    @JSONField(name = "user_id")
    private String userId;

    public String getUserId() {
        if(TextUtils.isEmpty(userId)){
            userId = "0";
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
