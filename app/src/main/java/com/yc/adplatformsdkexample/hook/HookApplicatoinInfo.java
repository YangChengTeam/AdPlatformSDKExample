package com.yc.adplatformsdkexample.hook;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

public class HookApplicatoinInfo extends ApplicationInfo {
    private static String originName = "";

    public static String getOriginName() {
        return originName;
    }

    public HookApplicatoinInfo(ApplicationInfo info){
        super(info);
    }
    @NonNull
    @Override
    public CharSequence loadLabel(@NonNull PackageManager pm) {
        originName = super.loadLabel(pm).toString();
        return "您的应用名称";
    }





}
