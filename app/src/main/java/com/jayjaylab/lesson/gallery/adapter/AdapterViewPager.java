package com.jayjaylab.lesson.gallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.util.Log;
import android.util.SparseArray;
import com.jayjaylab.lesson.gallery.fragment.FragmentFolder;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab1;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab2;
import com.jayjaylab.lesson.gallery.util.LogTag;
import com.jayjaylab.lesson.gallery.util.model.Image;
import com.jayjaylab.lesson.gallery.util.model.Thumbnail;

import java.util.List;
import java.util.Map;

/**
 * Created by HOMIN on 2016-05-25.
 */
public class AdapterViewPager extends FragmentStatePagerAdapter {
    final String TAG = AdapterViewPager.class.getSimpleName();
    int _numOfTabs;
//    List<Fragment> cacheFragment;
    Fragment[] cacheFragment;
    Map<String, List<Image>> map;
    Thumbnail[] thumbnails;

    public AdapterViewPager(android.support.v4.app.FragmentManager fm, int numOfTabs,
                            Map<String, List<Image>> map, Thumbnail[] thumbnails) {
        super(fm);
        this._numOfTabs = numOfTabs;
        cacheFragment = new Fragment[numOfTabs];
        this.map = map;
        this.thumbnails = thumbnails;
    }

    public void setDataSet(Map<String, List<Image>> map, Thumbnail[] thumbnails) {
        this.map = map;
        this.thumbnails = thumbnails;
    }

    public void setMap(Map<String, List<Image>> map) {
        this.map = map;
    }

    public void setThumbnails(Thumbnail[] thumbnails) {
        this.thumbnails = thumbnails;
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
                    ((FragmentTab1)fragment).setImages(thumbnails);
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
