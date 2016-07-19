package com.jayjaylab.lesson.gallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.jayjaylab.lesson.gallery.fragment.FragmentTab1;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab2;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab3;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab4;
import com.jayjaylab.lesson.gallery.util.LogTag;
import com.jayjaylab.lesson.gallery.util.model.Image;

import java.util.List;

/**
 * Created by HOMIN on 2016-05-25.
 */
public class AdapterViewPager extends FragmentStatePagerAdapter {
    final String TAG = AdapterViewPager.class.getSimpleName();
    int _numOfTabs;
    //  List<Fragment> cacheFragment;
    Fragment[] cacheFragment;
    List<Image> images;

    public AdapterViewPager(FragmentManager fm, int numOfTabs, List<Image> images) {
        super(fm);
        this._numOfTabs = numOfTabs;
        this.images = images;
        cacheFragment = new Fragment[numOfTabs];
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        if (LogTag.DEBUG) Log.d(TAG, "getItem() : position : " + position);
        if (cacheFragment[position] == null) {
            Log.d(TAG, "cache miss.");
            if (position == 0) {
                fragment = new FragmentTab1();
                ((FragmentTab1) fragment).setImages(images);
            }
            else if (position == 1) fragment = new FragmentTab2();
            else if (position == 2) fragment = new FragmentTab3();
            else if (position == 3) fragment = new FragmentTab4();

            cacheFragment[position] = fragment;
        } else {
            Log.d(TAG, "cache hit.");
            fragment = cacheFragment[position];
        }

        return fragment;

    }

    @Override
    public int getCount() {
        return _numOfTabs;
    }
}
