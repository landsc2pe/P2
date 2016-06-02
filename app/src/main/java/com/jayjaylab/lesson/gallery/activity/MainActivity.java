package com.jayjaylab.lesson.gallery.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.AdapterViewPager;
import com.jayjaylab.lesson.gallery.fragment.FragmentFolder;
import com.jayjaylab.lesson.gallery.util.LoaderImageFolder;
import com.jayjaylab.lesson.gallery.util.LogTag;
import com.jayjaylab.lesson.gallery.util.model.Image;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //    private static int msn = 1;
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    final String TAG = MainActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    FragmentFolder fragmentFolder;
    FragmentTransaction fragmentTransaction;
    LoaderImageFolder loaderImageFolder;

    AppBarLayout.LayoutParams params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//        final ActionBar ab = getSupportActionBar();
////        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.rcvr_tl_tabs);

        assert tabLayout != null;
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        loaderImageFolder = new LoaderImageFolder();
        loaderImageFolder.imageLoaderByMediaStore(this, new LoaderImageFolder.OnImageLoadListener() {
            @Override
            public void onLoad(Map<String, List<Image>> map) {
                if (LogTag.DEBUG) Log.d(TAG, "map : " + map);


                final ViewPager viewPager = (ViewPager) findViewById(R.id.rcvr_vp_pager);
                AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager(), tabLayout.getTabCount());

                assert viewPager != null;
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
                });
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });


        if (LogTag.DEBUG)
            Log.d(TAG, "externalCacheDir : " + getExternalCacheDir() + ", internalCacheDir : " + getCacheDir());

        //Permission Check with CursorLoader execute.
        checkPermission();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabLayout.getVisibility() == View.VISIBLE) {
                    Log.d(TAG, "INVISIBLE <= VISIBLE");

                    assert toolbar != null;
                    params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

                    if (LogTag.DEBUG) Log.d("FragmentChange", "A : "+params.getScrollFlags());
                    params.setScrollFlags(0);
                    if (LogTag.DEBUG) Log.d("FragmentChange", "Aa : "+params.getScrollFlags());


                    if (fragmentFolder == null) {
                        fragmentFolder = new FragmentFolder();
                        fragmentManager = getSupportFragmentManager();

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.main_layout, fragmentFolder);
                        fragmentTransaction.commit();
                        tabLayout.setVisibility(View.INVISIBLE);

                        if (LogTag.DEBUG) Log.d("FragmentChange", "new");

                    } else {

                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.show(fragmentFolder);
                        fragmentTransaction.commit();
                        tabLayout.setVisibility(View.INVISIBLE);

                        if (LogTag.DEBUG) Log.d("FragmentChange", "else");

                    }


//                    msn++;

                } else if (tabLayout.getVisibility() == View.INVISIBLE) {
                    Log.d(TAG, "INVISIBLE => VISIBLE");

                    assert toolbar != null;
                    params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                    if (LogTag.DEBUG) Log.d("FragmentChange", "B : "+params.getScrollFlags());
                    params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
                    if (LogTag.DEBUG) Log.d("FragmentChange", "Bb : "+params.getScrollFlags());


                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.hide(fragmentFolder);
                    fragmentTransaction.commit();
                    tabLayout.setVisibility(View.VISIBLE);
//                    msn--;

                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_setting_meun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)

    private void checkPermission() {
        if (LogTag.DEBUG) Log.d(TAG, "CheckPermission : " + ActivityCompat.checkSelfPermission(
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
            if (LogTag.DEBUG) Log.d(TAG, "permission is already allowed...");

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

                    if (LogTag.DEBUG) Log.d(TAG, "Permission always deny");

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
