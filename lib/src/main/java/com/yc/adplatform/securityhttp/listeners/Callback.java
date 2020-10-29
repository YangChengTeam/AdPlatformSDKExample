package com.yc.adplatform.securityhttp.listeners;


import com.yc.adplatform.securityhttp.net.entry.Response;

/**
 * Created by zhangkai on 16/9/20.
 */
public interface Callback<T> {
     void onSuccess(T resultInfo);
     void onFailure(Response response);
}
