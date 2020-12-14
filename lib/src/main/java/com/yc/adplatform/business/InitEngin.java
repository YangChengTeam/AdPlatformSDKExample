package com.yc.adplatform.business;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.TypeReference;
import com.yc.adplatform.securityhttp.domain.GoagalInfo;
import com.yc.adplatform.securityhttp.domain.ResultInfo;
import com.yc.adplatform.securityhttp.engin.BaseEngin;
import com.yc.uuid.UDIDInfo;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class InitEngin extends BaseEngin {

    private String url;

    public InitEngin(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    public Observable<ResultInfo<InitInfo>> getInItInfo(String url, String appId, UDIDInfo udidInfo) {
        this.url = url;
        Map<String, String> params = new HashMap<>();
        params.put("app_id", appId);
        params.put("oaid", udidInfo.getOaid());
        params.put("imei", TextUtils.isEmpty(udidInfo.getImei()) ? udidInfo.getImei() : GoagalInfo.get().uuid);
        params.put("imei2", udidInfo.getImei2());
        return rxpost(new TypeReference<ResultInfo<InitInfo>>() {
        }.getType(), params, true, true, true);
    }

}
