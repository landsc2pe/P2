package com.jayjaylab.lesson.gallery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.AdapterImageTab1;

/**
 * Created by Homin on 2016-04-07.
 */
public class FragmentTab1 extends Fragment {
    public static final String TAG = FragmentTab1.class.getSimpleName();
    private RecyclerView fragemntTab1;
    private GridLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_tab1, container, false);
        fragemntTab1 = (RecyclerView) rootView.findViewById(R.id.fragment_tab1);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragemntTab1.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 5);

        fragemntTab1.setLayoutManager(layoutManager);
        fragemntTab1.setAdapter(new AdapterImageTab1(this));


    }

}
