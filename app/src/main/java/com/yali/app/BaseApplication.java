package com.yali.app;

import android.app.Application;

/**
 * 该类
 *
 * @author jingyali
 * @version v1.0 2018/8/16 21:27
 */

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
//        GrowingIO.startWithConfiguration(this, new Configuration()
//                .useID()
//                .trackAllFragments()
//                .setTestMode(true) // 打开测试模式，数据即时发送
//                .setDebugMode(true) // 打印发送数据日志，应用上线一定要关闭
//                .setChannel("XXX应用商店"));

//        GrowingIO.startWithConfiguration(this, new Configuration().trackAllFragments().setChannel("XXX应用商店"));


    }
}
