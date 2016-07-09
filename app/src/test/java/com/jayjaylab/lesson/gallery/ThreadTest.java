package com.jayjaylab.lesson.gallery;

import android.util.Log;
import com.jayjaylab.lesson.gallery.sample.ThreadSample;
import com.jayjaylab.lesson.gallery.util.LogTag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by jjkim on 2016. 7. 9..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = android.os.Build.VERSION_CODES.LOLLIPOP)
public class ThreadTest {
    final String TAG = ThreadSample.class.getSimpleName();
    int count = 0;

    @Test
    public void testThread() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++) {
                    count++;
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++) {
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

        System.out.println("count : " + count);
    }
}
