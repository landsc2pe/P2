package com.jayjaylab.lesson.gallery.sample;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.jayjaylab.lesson.gallery.util.LogTag;

/**
 * Created by jjkim on 2016. 7. 9..
 */
public class ThreadSample {
    final String TAG = ThreadSample.class.getSimpleName();
    Handler handler;
    int count;

    public ThreadSample() {
        handler = new Handler();
        count = 0;
    }

    public void test() {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                if(LogTag.DEBUG) Log.d(TAG, "aaaa");
                if(LogTag.DEBUG) Log.d(TAG, "priority : " +
                    Thread.currentThread().getPriority());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(LogTag.DEBUG) Log.d(TAG, "t");
                        // ui handling
                    }
                });
            }
        };
        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                if(LogTag.DEBUG) Log.d(TAG, "bbbb");
            }
        };

        if(LogTag.DEBUG) Log.d(TAG, "main priority : " +
                Thread.currentThread().getPriority());

        Thread thread = new Thread(task1);
        thread.start();
    }

    public void test2() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i++) {
                    count++;
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100; i++) {
                    count++;
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(LogTag.DEBUG) Log.d(TAG, "count : " + count);
    }
}
