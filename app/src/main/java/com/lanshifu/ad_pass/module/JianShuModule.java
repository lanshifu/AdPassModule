package com.lanshifu.ad_pass.module;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.lanshifu.ad_pass.utils.LogUtil;
import com.lanshifu.ad_pass.version.JianShuVersionManager;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by lanxiaobin on 2018/1/29.
 * 简书去广告
 */

public class JianShuModule extends BaseModule {

    public static Handler mHandler = new Handler();

    private static final String TAG = "lxb-JianShuModule";

    private static JianShuVersionManager mJianShuVersionManager;


    public static void handleLoadPackage(final XC_LoadPackage.LoadPackageParam param) {

        if (!param.packageName.equals("com.jianshu.haruki")) {
            return;
        }

        hookApplication(param);

    }


    private static void hookApplication(final XC_LoadPackage.LoadPackageParam param) {

        LogUtil.d("开始hookApplication~");
        hook_method("com.tencent.tinker.loader.app.TinkerApplication", param.classLoader, "onBaseContextAttached", Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                        LogUtil.d(TAG, "hook HarukiApplicationLike,开始创建 JianShuVersionManager");
                        Context context = (Context) methodHookParam.args[0];
                        mJianShuVersionManager = new JianShuVersionManager(context);

                        hookSplashAdActivity(param);

                        hookSplashScreenActivity(param);

                        hookMainActivity(param);

                    }
                });
    }

    private static void hookMainActivity(XC_LoadPackage.LoadPackageParam param) {
        hook_method(mJianShuVersionManager.getSupportConfig().class_MainActivity, param.classLoader, "onCreate", Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Toast.makeText((Context) param.thisObject, "进入简书主页，蓝师傅破解", Toast.LENGTH_SHORT).show();
                        LogUtil.d(TAG, "hook MainActivity");
                    }
                });
    }

    private static void hookSplashScreenActivity(XC_LoadPackage.LoadPackageParam param) {
        hook_method(mJianShuVersionManager.getSupportConfig().class_SplashScreenActivity, param.classLoader, "onCreate", Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        //反射调用c方法
                        Object object = param.thisObject;
                        Class<?> aClass = object.getClass();
                        Method[] methods = aClass.getDeclaredMethods();
                        LogUtil.d(TAG, "SplashScreenActivity: methods.size = " + methods.length);

                        for (Method method : methods) {
//                            LogUtil.d(TAG, "hookDownload " + method.getName());
                            if (method.getName().equals(mJianShuVersionManager.getSupportConfig().method_SplashScreenActivity_pass_ad)) {
                                Log.d(TAG, "SplashScreenActivity 跳过吧~");
                                Toast.makeText((Context) param.thisObject, "蓝师傅破解，跳过首页广告2~", Toast.LENGTH_SHORT).show();
                                method.setAccessible(true);
                                method.invoke(object);

                            }
                        }

                    }
                });
    }

    private static void hookSplashAdActivity(XC_LoadPackage.LoadPackageParam param) {
        hook_method(mJianShuVersionManager.getSupportConfig().class_SplashAdActivity, param.classLoader, "onCreate", Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Toast.makeText((Context) param.thisObject, "广告页，自动跳过", Toast.LENGTH_SHORT).show();
                        //反射调用c方法
                        Object object = param.thisObject;
                        Class<?> aClass = object.getClass();
                        Method[] methods = aClass.getDeclaredMethods();
                        LogUtil.d(TAG, "SplashAdActivity: methods.size = " + methods.length);

                        for (Method method : methods) {
//                            Log.d(TAG, "hookDownload " + method.getName());
                            if (method.getName().equals(mJianShuVersionManager.getSupportConfig().method_SplashAdActivity_pass_ad)) {
                                LogUtil.d(TAG, "SplashAdActivity 跳过吧~");
                                Toast.makeText((Context) param.thisObject, "广告页1，自动跳过", Toast.LENGTH_SHORT).show();
                                method.setAccessible(true);
                                method.invoke(object);

                            }
                        }

                    }
                });
    }


}
