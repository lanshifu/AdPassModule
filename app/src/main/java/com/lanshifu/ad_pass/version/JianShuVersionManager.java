package com.lanshifu.ad_pass.version;

import android.content.Context;

import com.lanshifu.ad_pass.utils.AppUtil;
import com.lanshifu.ad_pass.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class JianShuVersionManager {

    private final static Map<String, Class<? extends Config>> CONFIG_MAP_JIANSHU = new HashMap<>();

    static {
        CONFIG_MAP_JIANSHU.put("4.10.1", Config_4101.class);
    }


    public static Context mContext;

    private Config mVersionConfig;


    public JianShuVersionManager(Context context){
        mContext = context;
    }



    private static String mCcurrentVersionName;

    public Config getSupportConfig() {

        return getSupportConfig(CONFIG_MAP_JIANSHU.get(getmCcurrentVersionName()));
    }

    private Config getSupportConfig(Class<? extends Config> vClass) {

        if (vClass == null) return null;

        if (mVersionConfig == null) {
            try {
                // 创建实例
                mVersionConfig = vClass.newInstance();
            } catch (Throwable tr) {
                LogUtil.e("创建版本配置异常");
            }
        }
        return mVersionConfig;
    }


    private static String getmCcurrentVersionName(){
        if (mCcurrentVersionName == null){
            mCcurrentVersionName = AppUtil.getVersionName(mContext);
        }
        if (mCcurrentVersionName == null){
            mCcurrentVersionName = "4.10.1";
        }
        return mCcurrentVersionName;
    }


    public boolean isSupportVersion() {
        return CONFIG_MAP_JIANSHU.containsKey(getmCcurrentVersionName());
    }


    public static class Config {

        public String PACKAGE_NAME = "com.jianshu.haruki";

        public String class_Application = "com.baiji.jianshu.HarukiApplicationLike";

        //广告页1
        public String class_SplashAdActivity = "com.baiji.jianshu.ui.splash.SplashAdActivity";

        //广告页2
        public String class_SplashScreenActivity = "com.baiji.jianshu.ui.splash.SplashScreenActivity";

        public String class_MainActivity = "com.baiji.jianshu.MainActivity";

        //跳过广告1方法
        public String method_SplashAdActivity_pass_ad = "c";

        //跳过广告1方法
        public String method_SplashScreenActivity_pass_ad = "j";

    }



    public static class Config_4101 extends Config {

        public Config_4101(){

            LogUtil.d("Config_4101");

            //广告页1
            class_SplashAdActivity = "com.baiji.jianshu.ui.splash.SplashAdActivity";

            //广告页2
            class_SplashScreenActivity = "com.baiji.jianshu.ui.splash.SplashScreenActivity";

            class_MainActivity = "com.baiji.jianshu.MainActivity";

            //跳过广告1方法
            method_SplashAdActivity_pass_ad = "c";

            //跳过广告1方法
            method_SplashScreenActivity_pass_ad = "j";
        }
    }
}
