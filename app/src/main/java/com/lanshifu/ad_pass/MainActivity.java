package com.lanshifu.ad_pass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_tip = (TextView) findViewById(R.id.tv_tip);
        if (isOpen()){
            tv_tip.setText("模块已经启动");
            tv_tip.setTextColor(getColor(R.color.green));
        }else {
            tv_tip.setText("模块未启动，请到XposedInstaller中勾选模块并重启");
            tv_tip.setTextColor(getColor(R.color.red));

        }

    }

    public boolean isOpen(){
        return false;
    }
}
