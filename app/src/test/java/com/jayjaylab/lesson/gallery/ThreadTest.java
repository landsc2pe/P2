package com.jayjaylab.lesson.gallery;

import android.os.AsyncTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by jjkim on 2016. 7. 6..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ThreadTest {
    @Test
    public void test() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("a");
            }

            void println() {

            }
        });
        thread.start();
    }

    @Test
    public void test1() {
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                System.out.println("b");
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                System.out.println("c");
            }
        };
        asyncTask.execute();
    }
}
