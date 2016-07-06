package com.jayjaylab.lesson.gallery.sample;

import android.os.AsyncTask;
import android.util.Log;
import com.jayjaylab.lesson.gallery.util.LogTag;

/**
 * Created by jjkim on 2016. 7. 6..
 */
public class ThreadSample {


    public void test() {
        Sample1<Integer> sample = new Sample1<>(Integer.valueOf(1));

        Sample2<Integer, Integer, Integer> sample2 =
                new Sample2<Integer, Integer, Integer>();
    }

    public void testAsyncTAsk() {
        if(LogTag.DEBUG) Log.d("sample", "1");
        test();
        if(LogTag.DEBUG) Log.d("sample", "2");

        AsyncTask<Integer, Integer, Integer> asyncTask =
                new AsyncTask<Integer, Integer, Integer>() {
                    @Override
                    protected Integer doInBackground(Integer... params) {
                        if(LogTag.DEBUG) Log.d("sample",
                                "doInBackground() : thread name : " +
                                Thread.currentThread().getName());

                        publishProgress(10);

                        return Integer.valueOf(10);
                    }

                    @Override
                    protected void onProgressUpdate(Integer... values) {
                        if(LogTag.DEBUG) Log.d("sample",
                                "onProgressUpdate() : thread name : " +
                                Thread.currentThread().getName());
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute(Integer integer) {
                        if(LogTag.DEBUG) Log.d("sample",
                                "onPostExecute() : thread name : " +
                                Thread.currentThread().getName());
                        super.onPostExecute(integer);
                    }
                };
        asyncTask.execute();
    }
}
