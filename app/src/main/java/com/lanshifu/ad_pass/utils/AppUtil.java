package com.lanshifu.ad_pass.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtil {

    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
            LogUtil.d("获取到版本号 = " + name + "-- "+ context.getPackageName());
            return name;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e.getMessage());
            return null;
        }


    }
}
