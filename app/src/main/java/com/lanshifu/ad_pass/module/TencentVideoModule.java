package com.lanshifu.ad_pass.module;


import android.content.Context;
import android.widget.Toast;

import com.lanshifu.ad_pass.utils.LogUtil;
import com.lanshifu.ad_pass.version.TencentVideoVersionManager;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 腾讯视频
 */
public class TencentVideoModule extends BaseModule {

    private static TencentVideoVersionManager mTencentVideoVersionManager;

    public static void handleLoadPackage(final XC_LoadPackage.LoadPackageParam param) {

        if (!param.packageName.equals(TencentVideoVersionManager.PACKAGE_NAME)) {
            return;
        }

        hookApplication(param);


    }

    private static void hookApplication(final XC_LoadPackage.LoadPackageParam param) {
        LogUtil.d("开始 hook QQLiveApplication");
        hook_method(
                "com.tencent.qqlive.ona.base.QQLiveApplication",
                param.classLoader,
                "attachBaseContext",
                Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                        LogUtil.d("hook QQLiveApplication 成功");
                        Context context = (Context) methodHookParam.args[0];
                        mTencentVideoVersionManager = new TencentVideoVersionManager(context);

//                        hookQQLiveLog(param);

                        hookVideoInfo(param);

                        hookHomeAD(param);


                    }
                });
    }

    private static void hookHomeAD(XC_LoadPackage.LoadPackageParam param) {
        LogUtil.d("开始 hookHomeAD");
        hook_method(
                mTencentVideoVersionManager.getSupportConfig().class_TadUtil,
                param.classLoader,
                mTencentVideoVersionManager.getSupportConfig().method_handlerAdJump,
                Context.class,
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                        param.setResult(false);
                        LogUtil.d("跳过首页广告");
                        if (mTencentVideoVersionManager.getContext() != null){
                            Toast.makeText(mTencentVideoVersionManager.getContext(),"跳过首页广告~",Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    private static void hookQQLiveLog(XC_LoadPackage.LoadPackageParam param) {
        LogUtil.d("开始 hookQQLiveLog");
        hook_method(
                mTencentVideoVersionManager.getSupportConfig().class_QQLiveLog,
                param.classLoader,
                mTencentVideoVersionManager.getSupportConfig().method_log,
                String.class,
                String.class,
                String.class,
                int.class,

                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        LogUtil.d("hookQQLiveLog：" + param.args[0] +"-" + param.args[1]+"-" + param.args[2]);
                    }
                });
    }


    private static void hookVideoInfo(XC_LoadPackage.LoadPackageParam param) {
        LogUtil.d("开始 hookVideoInfo");
        hook_method(
                mTencentVideoVersionManager.getSupportConfig().class_VideoInfo,
                param.classLoader,
                mTencentVideoVersionManager.getSupportConfig().method_isAdSkip,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(true);
                        LogUtil.d("跳过腾讯视频广告");
                        if (mTencentVideoVersionManager.getContext() != null){
                            Toast.makeText(mTencentVideoVersionManager.getContext(),"跳过腾讯视频广告~",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
