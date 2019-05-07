package com.example.demo_7_handler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class HandlerIsWhoseActivity extends AppCompatActivity {
    private static final String TAG = "handlertest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandlerThread handlerThread = new HandlerThread("jiataitest");
        handlerThread.start();
        Handler handler = new MyHandler(handlerThread.getLooper());
        handler.sendEmptyMessage(1);

        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(HandlerIsWhoseActivity.this, LeakActivity.class);
                startActivity(it);
            }
        });
    }

    private class MyHandler extends Handler{
        MyHandler(Looper looper){
            super(looper);
            Log.e(TAG, "MyHandler constructor");
            printThread();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "MyHandler handleMessage");
            printThread();
        }

        public void printThread(){
            Log.e(TAG, "the thread is " + Thread.currentThread().toString());
        }
    }
}
