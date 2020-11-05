package com.yc.adplatformsdkexample.hook;

import android.content.pm.PackageInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IPackageManagerProxy implements InvocationHandler {
    private Object mIPackageManager; // origin IPackageManager.

    public IPackageManagerProxy(Object iPackageManager) {
        mIPackageManager = iPackageManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //public ApplicationInfo getApplicationInfo(String packageName, int flags, int userId)
        if (method.getName().equals("getPackageInfo")) {
            PackageInfo result = (PackageInfo) method.invoke(mIPackageManager, args);
            if(result != null){
                result.signatures = HookSignature.getSignature();
                result.packageName = "com.whychl.TrickyCastle";
            }
            return result;
        }
        return method.invoke(mIPackageManager, args);
    }


}
