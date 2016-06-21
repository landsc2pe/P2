package com.jayjaylab.lesson.gallery.presenter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.jayjaylab.lesson.gallery.util.LoaderImageFolder;
import com.jayjaylab.lesson.gallery.util.LogTag;
import com.jayjaylab.lesson.gallery.util.model.Image;
import com.jayjaylab.lesson.gallery.view.ViewMainActivityInterface;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/**
 * Created by jjkim on 2016. 6. 14..
 */
public class PresenterMainActivity implements PresenterMainActivityInterface {
    final String TAG = PresenterMainActivity.class.getSimpleName();
    WeakReference<ViewMainActivityInterface> view;
    LoaderImageFolder loader;

    public PresenterMainActivity(ViewMainActivityInterface activity) {
        this.view = new WeakReference<>(activity);
        loader = new LoaderImageFolder();
    }

    @Override
    public void loadImage(Activity activity) {
        if(activity != null && view.get() != null) {
            // TODO: 2016. 6. 18. do something.
            LoaderImageFolder.OnImageLoadListener listener = new LoaderImageFolder.OnImageLoadListener() {
                @Override
                public void onLoad(Map<String, List<Image>> map, List<Image> originalImages) {
                    if (LogTag.DEBUG) Log.d(TAG, "map : " + map);
                    if(view.get() != null) {
                        view.get().storeImageMap(map);
                        view.get().showEveryImage(originalImages);
                    }
                }
            };
            loader.imageLoaderByMediaStore(activity, listener);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkPermission(Activity activity) {
        if (LogTag.DEBUG) Log.d(TAG, "CheckPermission : " + ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.WRITE_EXTERNAL_STORAGE));

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (activity.shouldShowRequestPermissionRationale(Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(activity, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            }

            activity.requestPermissions(new String[]{Manifest.permission
                    .READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

            return false;
        } else {
            if (LogTag.DEBUG) Log.d(TAG, "permission is already allowed...");
            return true;
        }
    }

}
