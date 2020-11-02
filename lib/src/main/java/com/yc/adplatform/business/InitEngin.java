package com.yc.adplatform.business;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.yc.adplatform.securityhttp.domain.ResultInfo;
import com.yc.adplatform.securityhttp.engin.BaseEngin;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class InitEngin extends BaseEngin {

    public InitEngin(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return "http://qudaotj.tools86.com/api/v1.user/imeiLogin";
    }

    public Observable<ResultInfo<InitInfo>> getInItInfo(String appId) {
        Map<String, String> params = new HashMap<>();
        params.put("app_id", appId);
        return rxpost(new TypeReference<ResultInfo<InitInfo>>() {
        }.getType(), params, true, true, true);
    }

}
