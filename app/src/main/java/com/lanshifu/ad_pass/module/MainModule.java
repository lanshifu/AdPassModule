package com.lanshifu.ad_pass.module;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;



import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by lanxiaobin on 2018/1/29.
 */

public class MainModule extends BaseModule {


    /**
     * 入口，通过反射调用
     *
     * @param param
     */
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam param) {


        try {
            if (param.packageName.equals("com.lanshifu.ad_pass")) {
                hook_method("com.lanshifu.ad_pass.MainActivity", param.classLoader, "isOpen", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d("lxb", "hookMainActivity -- >initView");
                        param.setResult(true);
                        Toast.makeText((Context) param.thisObject, "模块已经启动", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        JianShuModule.handleLoadPackage(param);

        TencentVideoModule.handleLoadPackage(param);
    }

}
