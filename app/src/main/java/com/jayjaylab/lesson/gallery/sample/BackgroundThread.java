package com.jayjaylab.lesson.gallery.sample;

import android.os.Process;

/**
 * Created by jjkim on 2016. 7. 9..
 */
public class BackgroundThread extends Thread {
    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        super.run();
    }
}
