package com.lanshifu.ad_pass.version;

import android.content.Context;

import com.lanshifu.ad_pass.utils.AppUtil;
import com.lanshifu.ad_pass.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

public class TencentVideoVersionManager {

    public static String PACKAGE_NAME = "com.tencent.qqlive";

    private static final String TAG = "TencentVideoVersionManager";

    private final static Map<String, Class<? extends Config>> CONFIG_MAP = new HashMap<>();

    static {
        CONFIG_MAP.put("7.0.8.19572", Config_70819572.class);
    }


    private Context mContext;

    public Context getContext(){
        return mContext;
    }

    private Config mVersionConfig;


    public TencentVideoVersionManager(Context context){
        mContext = context;
    }


    private static String mCcurrentVersionName;

    public Config getSupportConfig() {

        return getSupportConfig(CONFIG_MAP.get(getmCcurrentVersionName()));
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


    private String getmCcurrentVersionName(){
        if (mCcurrentVersionName == null){
            mCcurrentVersionName = AppUtil.getVersionName(getContext());
        }
        if (mCcurrentVersionName == null){
            mCcurrentVersionName = "7.0.8.19572";
        }
        return mCcurrentVersionName;
    }

    
    public boolean isSupportVersion() {
        return CONFIG_MAP.containsKey(getmCcurrentVersionName());
    }


    public static class Config {

        public String PACKAGE_NAME = "com.tencent.qqlive";

        public String class_VideoInfo = "com.tencent.qqlive.ona.player.VideoInfo";

        public String class_QQLiveLog = "com.tencent.qqlive.qqlivelog.QQLiveLog";

        public String class_TadUtil = "com.tencent.qqlive.tad.utils.TadUtil";


        public String method_isAdSkip = "isAdSkip";

        public String method_log = "log";

        public String method_handlerAdJump = "handlerAdJump";


    }



    public static class Config_70819572 extends Config {

        public Config_70819572(){
            LogUtil.d(TAG,"Config_70819572");

        }
    }
}
