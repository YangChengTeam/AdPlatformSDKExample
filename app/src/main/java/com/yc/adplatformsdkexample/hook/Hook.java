package com.yc.adplatformsdkexample.hook;

import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Hook {
    public static void hookPms(Context context) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Reflection.unseal(context);

        // Get ActivityThread object.
        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        Object sCurrentActivityThread = currentActivityThreadMethod.invoke(null);
        // Get sPackageManager object.
        Field sPackageManagerField = activityThreadClass.getDeclaredField("sPackageManager");
        sPackageManagerField.setAccessible(true);
        Object sPackageManager = sPackageManagerField.get(sCurrentActivityThread);

        //Generate proxy object.
        Class<?> iPackageManagerClass = Class.forName("android.content.pm.IPackageManager");
        Object iPackageManagerProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iPackageManagerClass}, new IPackageManagerProxy(sPackageManager));

        //Replace proxy object.
        sPackageManagerField.set(sCurrentActivityThread, iPackageManagerProxy);

        //Replace mPM to proxy object in ApplicationPackageManager.
        PackageManager packageManager = context.getPackageManager();
        Field mPMField = packageManager.getClass().getDeclaredField("mPM");
        mPMField.setAccessible(true);
        mPMField.set(packageManager, iPackageManagerProxy);
    }

}
