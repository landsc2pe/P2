package com.jayjaylab.lesson.gallery.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
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

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.AdapterViewPager;
import com.jayjaylab.lesson.gallery.event.ClickEvent;
import com.jayjaylab.lesson.gallery.event.ClickEventId;
import com.jayjaylab.lesson.gallery.fragment.FragmentFolder;
import com.jayjaylab.lesson.gallery.presenter.PresenterMainActivity;
import com.jayjaylab.lesson.gallery.presenter.PresenterMainActivityInterface;
import com.jayjaylab.lesson.gallery.sample.ThreadSample;
import com.jayjaylab.lesson.gallery.util.LogTag;
import com.jayjaylab.lesson.gallery.util.eventbus.EventBus;
import com.jayjaylab.lesson.gallery.util.model.Image;
import com.jayjaylab.lesson.gallery.view.ViewMainActivityInterface;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, ViewMainActivityInterface {
    final String TAG = getClass().getSimpleName();

    FragmentManager fragmentManager;
    FragmentFolder fragmentFolder;
    FragmentTransaction fragmentTransaction;

    AppBarLayout.LayoutParams params;
    PresenterMainActivityInterface presenter;

    // Views
    Toolbar toolbar;
    TabLayout tabLayout;
    Map hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_main);
        presenter = new PresenterMainActivity(this);
        EventBus.getInstance().getBus().register(this);

        init();
        if (presenter.checkPermission(this)) {
            presenter.loadImage(this);
        }

//        test();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getInstance().getBus().unregister(this);
    }

    public void test() {
//        AsyncTaskSample threadSample = new AsyncTaskSample();
//        threadSample.testAsyncTask();

        ThreadSample threadSample = new ThreadSample();
//        threadSample.test();
        threadSample.test();
    }

    void init() {
        setViews();
    }

    void setViews() {
        setToolbar();
        setTabLayout();
        setFloatingActionButton();
        setDrawerLayout();
    }

    void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    void setTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.rcvr_tl_tabs);
        if (tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
            tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
            tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
            tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        }
    }

    void setFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getInstance().getBus().post(
                            new ClickEvent(ClickEventId.FLOATING_ACTION_BUTTON));

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    if (tabLayout.getVisibility() == View.VISIBLE) {
                        Log.d(TAG, "INVISIBLE <= VISIBLE");
                        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();

                        if (fragmentFolder == null) {
                            fragmentFolder = FragmentFolder.newInstance(hashMap);
                            fragmentTransaction.add(R.id.main_layout, fragmentFolder);
                            tabLayout.setVisibility(View.INVISIBLE);
                            if (LogTag.DEBUG) Log.d("FragmentChange", "new");

                        } else {
                            fragmentTransaction.show(fragmentFolder);
                            tabLayout.setVisibility(View.INVISIBLE);
                            if (LogTag.DEBUG) Log.d("FragmentChange", "else");

                        }
                    } else if (tabLayout.getVisibility() == View.INVISIBLE) {
                        Log.d(TAG, "INVISIBLE => VISIBLE");

                        assert toolbar != null;
                        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
                        if (LogTag.DEBUG) Log.d("FragmentChange", "B : " + params.getScrollFlags());
                        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
                        if (LogTag.DEBUG)
                            Log.d("FragmentChange", "Bb : " + params.getScrollFlags());
                        fragmentTransaction.hide(fragmentFolder);
                        tabLayout.setVisibility(View.VISIBLE);
                    }

                    fragmentTransaction.commit();
                }
            });
    }

    void setDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    public void setViewPager(List<Image> originalImages) {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.rcvr_vp_pager);
        AdapterViewPager adapter = new AdapterViewPager
                (getSupportFragmentManager(), tabLayout.getTabCount(), originalImages);
//                adapter.setDataSet(map, thumbnails);

        Log.d(TAG, "adapter : " + adapter);
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

    @Override
    public void storeImageMap(Map<String, List<Image>> map) {
        hashMap = map;
    }

    @Override
    public void showEveryImage(List<Image> originalImages) {
        setViewPager(originalImages);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PresenterMainActivityInterface.MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    presenter.loadImage(this);
                } else {
                    if (LogTag.DEBUG) Log.d(TAG, "Permission always deny");
                }
                break;
        }
    }

}

