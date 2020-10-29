package com.yc.adplatform.securityhttp.net.listeners;


import com.yc.adplatform.securityhttp.net.entry.Response;

/**
 * Created by zhangkai on 16/8/30.
 */
public interface OnHttpResonseListener {
    void onSuccess(Response response);
    void onFailure(Response response);
}
