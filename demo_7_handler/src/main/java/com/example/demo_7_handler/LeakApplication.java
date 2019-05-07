package com.example.demo_7_handler;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by jiatai on 18-2-27.
 */

public class LeakApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        setupLeakCanary();
    }

    protected void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        enabledStrictMode();
        LeakCanary.install(this);
    }

    private static void enabledStrictMode() {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
////                .detectDiskReads()  //detect [dɪˈtɛkt] vt.查明，发现; 洞察; 侦察，侦查;
////                .detectDiskWrites()
////                .detectNetwork()
//                .detectAll() //
//                .penaltyLog() //
//                .penaltyDialog() //
////                .penaltyDeath() // 违规直接死掉了
//                .build());

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyDialog()

                    .penaltyFlashScreen()
                    .penaltyLog() //
//                    .penaltyDeath() //

                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
//                    .penaltyDeath()
                    .penaltyLog()
                    .build());
        }
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

    }




}
