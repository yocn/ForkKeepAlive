package com.boolbird.keepalive.demo;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.boolbird.keepalive.KeepAlive;
import com.boolbird.keepalive.KeepAliveConfigs;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext");
        KeepAliveConfigs configs = new KeepAliveConfigs(
                new KeepAliveConfigs.Config(getPackageName() + ":resident",
                        Service1.class.getCanonicalName()));
        configs.ignoreBatteryOptimization();
        // configs.rebootThreshold(10*1000, 3);
        configs.setOnBootReceivedListener(new KeepAliveConfigs.OnBootReceivedListener() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive boot");
                // 设置服务自启
                context.startService(new Intent(context, Service1.class));
            }
        });
        KeepAlive.init(base, configs);
    }
}
