package com.example.demo_7_handler;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

public class LeakActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 会造成内存泄漏
        new MyThread(this).start();
        // 会造成内存泄漏
//        LeakThread leakThread = new LeakThread();
//        leakThread.start();

        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LeakActivity.this,HandlerIsWhoseActivity.class);
                startActivity(it);
            }
        });
    }

    private static class MyHandler extends Handler{
        private WeakReference<LeakActivity> mActivity;

        public  MyHandler(LeakActivity activity){
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LeakActivity mainActivity = mActivity.get();
            if (mainActivity == null) {
                return;
            }
            super.handleMessage(msg);
        }
    }

    private static class MyThread extends Thread{

        private WeakReference<LeakActivity> mActivity;

        public  MyThread(LeakActivity activity){
            super("zjtThread");
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            LeakActivity mainActivity = mActivity.get();
            if (mainActivity == null) {
               return;
            }
            super.run();
            for(int i = 0 ; i < 500 ; i ++){
                Log.d("zjt", "i = " + i);
                if(mainActivity == null){
                    Log.e("zjt", "the weakreference is null");
                    return;
                }else {
                    Log.e("zjt", "the mainActivity is" + mainActivity);
                }
                SystemClock.sleep(2000);
            }

            /*Looper.prepare();
            MyHandler handler = new MyHandler(mainActivity);
            handler.sendEmptyMessage(0);
            Looper.loop();*/
        }
    }

    class LeakThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(6 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zjt", "onDestory");
        System.gc();
    }
}
