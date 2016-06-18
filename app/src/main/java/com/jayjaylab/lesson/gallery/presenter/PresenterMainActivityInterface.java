package com.jayjaylab.lesson.gallery.presenter;

import android.app.Activity;

/**
 * Created by jjkim on 2016. 6. 14..
 */
public interface PresenterMainActivityInterface {
    int MY_PERMISSION_REQUEST_STORAGE = 100;

    void loadImage(Activity activity);

    /**
     * Checks permission
     * @param activity
     * @return true if the permission is allowed, false otherwise.
     */
    boolean checkPermission(Activity activity);
}
