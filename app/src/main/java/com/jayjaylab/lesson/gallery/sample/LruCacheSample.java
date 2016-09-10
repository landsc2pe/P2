package com.jayjaylab.lesson.gallery.sample;

import android.util.LruCache;

/**
 * Created by jayjay on 2016. 8. 6..
 */
public class LruCacheSample {
    LruCache<String, String> lruCache;

    final String msg1 = "a";

    public LruCacheSample() {
        lruCache = new LruCache<>(100);
    }


}
