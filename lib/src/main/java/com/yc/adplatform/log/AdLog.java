package com.yc.adplatform.log;

import com.yc.adplatform.securityhttp.domain.GoagalInfo;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AdLog {
    public static void sendLog(final String ip, final int port, final String appId, final String userId, final String adPosition, final String adCode, final String adType) {
        String data = GoagalInfo.get().uuid + "," + userId + "," + appId + "," + adPosition + "," + adCode + "," + adType;
        Observable.just(data).observeOn(Schedulers.newThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String data) {
                LogSDK.getInstance().send(ip, port, data);
            }
        });
    }
}
