package com.jayjaylab.lesson.gallery.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.fragment.Fragment1;
import com.jayjaylab.lesson.gallery.fragment.Fragment2;
import com.jayjaylab.lesson.gallery.model.Image;
import com.jayjaylab.lesson.gallery.util.LoaderImage;
import com.jayjaylab.lesson.gallery.util.LogTag;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static int msn = 1;

    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    final String TAG = MainActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    Fragment1 fragment_first;
    Fragment2 fragment_second;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (LogTag.DEBUG)
            Log.d(TAG, "externalCacheDir : " + getExternalCacheDir() + ", internalCacheDir : " + getCacheDir());

        //Permission Check with CursorLoader execute.
        checkPermission();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_first = new Fragment1();
        fragmentTransaction.replace(R.id.main_layout, fragment_first, Fragment1.TAG);
        fragmentTransaction.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (msn == 1) {
                    fragment_second = new Fragment2();
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_layout, fragment_second, "ManageFragment");
                    fragmentManager.findFragmentByTag("ManageFragment");
//                    fragment_second.sortImagePath(loaderImage.getMap());
                    fragmentTransaction.commit();

                    msn++;

                } else if (msn == 2) {
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_layout, fragment_first, "MainFragment");
                    fragmentTransaction.commit();

                    msn--;
                }
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.M)

    private void checkPermission() {
        if(LogTag.DEBUG)Log.d(TAG, "CheckPermission : " + ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to write the permission.
                Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);

            // MY_PERMISSION_REQUEST_STORAGE is an
            // app-defined int constant

        } else {
            if(LogTag.DEBUG)Log.d(TAG, "permission is already allowed...");

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    if(LogTag.DEBUG) Log.d(TAG, "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }


//    void getImageUriInBackground() {
//        getLoaderManager().initLoader(LOADER_ID_THUMBNAIL, null, loaderImage.getLoaderCallbacksForThumbnails());
//        getLoaderManager().initLoader(LOADER_ID_IMAGE, null, loaderImage.getLoaderCallbacksForOriginalImages());
//    }

}
