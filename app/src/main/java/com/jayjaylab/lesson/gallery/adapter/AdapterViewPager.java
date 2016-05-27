package com.jayjaylab.lesson.gallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jayjaylab.lesson.gallery.fragment.FragmentFolder;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab1;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab2;

/**
 * Created by HOMIN on 2016-05-25.
 */
public class AdapterViewPager extends FragmentStatePagerAdapter {
    int _numOfTabs;

    public AdapterViewPager(android.support.v4.app.FragmentManager fm, int numOfTabs) {
        super(fm);
        this._numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentTab1 tab1 = new FragmentTab1(); // Fragment 는 알아서 만들자
                return tab1;
            case 1:
                FragmentTab2 tab2 = new FragmentTab2();
                return tab2;
            case 2:
                FragmentFolder tab3 = new FragmentFolder();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return _numOfTabs;
    }
}
