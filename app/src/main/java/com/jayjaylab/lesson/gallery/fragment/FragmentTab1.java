package com.jayjaylab.lesson.gallery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.AdapterImageTab1;
import com.jayjaylab.lesson.gallery.util.model.Image;

import java.util.List;


/**
 * Created by Homin on 2016-04-07.
 */
public class FragmentTab1 extends Fragment {
    public static final String TAG = FragmentTab1.class.getSimpleName();
    private RecyclerView fragmentTab1;
    private GridLayoutManager layoutManager;
    List<Image> images;
//
//    public static FragmentTab1 newInstance(Thumbnail thumbail, Map<String, List<Image>> map) {
//        Bundle args = new Bundle();
//
//        Log.d(TAG, "map : " + map + ", thumbnail : " + thumbail);
//        // TODO: 2016. 6. 7.
//        Parcelable mapParcelable = Parcels.wrap(map);
//        args.putParcelable("map", mapParcelable);
//        args.putParcelable("thumbnail", thumbail);
//        FragmentTab1 fragmentTab1 = new FragmentTab1();
//        fragmentTab1.setArguments(args);
//        return fragmentTab1;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_fragment_tab1, container, false);
        fragmentTab1 = (RecyclerView) rootView.findViewById(R.id.fragment_tab1);

        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, "arrayThumbnail : " + images);

        fragmentTab1.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 5);

        fragmentTab1.setLayoutManager(layoutManager);
        fragmentTab1.setAdapter(new AdapterImageTab1(this, images));
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
