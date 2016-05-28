package com.jayjaylab.lesson.gallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.util.Log;
import android.util.SparseArray;
import com.jayjaylab.lesson.gallery.fragment.FragmentFolder;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab1;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab2;
import com.jayjaylab.lesson.gallery.util.LogTag;

import java.util.List;

/**
 * Created by HOMIN on 2016-05-25.
 */
public class AdapterViewPager extends FragmentStatePagerAdapter {
    final String TAG = AdapterViewPager.class.getSimpleName();
    int _numOfTabs;
//    List<Fragment> cacheFragment;
    Fragment[] cacheFragment;

    public AdapterViewPager(android.support.v4.app.FragmentManager fm, int numOfTabs) {
        super(fm);
        this._numOfTabs = numOfTabs;
        cacheFragment = new Fragment[numOfTabs];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                if(LogTag.DEBUG) Log.d(TAG, "getItem() : position : " + position);
                if(cacheFragment[position] == null) {
                    Log.d(TAG, "cache miss.");
                    fragment = new FragmentTab1(); // Fragment 는 알아서 만들자
                    cacheFragment[position] = fragment;
                } else {
                    Log.d(TAG, "cache hit.");
                    fragment = cacheFragment[position];
                }
            case 1:
                if(LogTag.DEBUG) Log.d(TAG, "getItem() : position : " + position);

                if(cacheFragment[position] == null) {
                    Log.d(TAG, "cache miss.");
                    fragment = new FragmentTab2();
                    cacheFragment[position] = fragment;
                } else {
                    Log.d(TAG, "cache hit.");
                    fragment = cacheFragment[position];
                }
            case 2:
                if(LogTag.DEBUG) Log.d(TAG, "getItem() : position : " + position);

                if(cacheFragment[position] == null) {
                    Log.d(TAG, "cache miss.");
                    fragment = new FragmentFolder();
                    cacheFragment[position] = fragment;
                } else {
                    Log.d(TAG, "cache hit.");
                    fragment = cacheFragment[position];
                }
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return _numOfTabs;
    }
}
