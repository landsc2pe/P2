package com.jayjaylab.lesson.gallery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayjaylab.lesson.gallery.util.ListenerOnClick;
import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.AdapterImage;
import com.jayjaylab.lesson.gallery.adapter.MyAdapter;
import com.jayjaylab.lesson.gallery.adapter.AdapterAbove;
import com.jayjaylab.lesson.gallery.adapter.MyData;
import com.jayjaylab.lesson.gallery.model.Image;
import com.jayjaylab.lesson.gallery.util.LogTag;

import java.io.File;
import java.util.*;

/**
 * Created by Homin on 2016-04-07.
 */
public class Fragment2 extends Fragment {
    final String TAG = Fragment2.class.getSimpleName();
    public static String KEY_IMAGE = "image";


    Context mContext;

    int mapSize;
    List<String> folderName;

    Map<String, List<Image>> hashMap;
    List<String> keyArray;
    List<Image> imageFiles;


    private RecyclerView recyclerViewAbove;
    private RecyclerView recyclerViewMenu;
    private RecyclerView recyclerViewGallery;
    private RecyclerView.Adapter adapterMenu, adapterAbove;
    private ArrayList<MyData> myDataset;
    private File mGalleryFolder;
    private String GALLERY_LOCATION = ".thumbnails";
    private AdapterImage adapterImage;

//    public static Fragment2 newInstance(String[] imageFiles) {
//        Fragment2 fragment = new Fragment2();
//        Bundle args = new Bundle();
//        args.putStringArray(KEY_IMAGE, imageFiles);
//
//        fragment.setArguments(args);
//        return fragment;
//    }
//

    public void sortImagePath(Map<String, List<Image>> map) {
        mapSize = map.size();
        hashMap = map;

        folderName = new ArrayList<>();
        keyArray = new ArrayList<>();

        Iterator<String> iter = hashMap.keySet().iterator();

        while (iter.hasNext()) {
            String keys = iter.next();
            keyArray.add(keys);
            String[] it = keys.split("/");
            int a = it.length - 1;
            folderName.add(it[a]);
        }

//        folderName = hashMap.keySet().toArray(new String[map.size()]);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        createImageGallery();

        View rootView = inflater.inflate(R.layout.manage_layout, container, false);
        recyclerViewAbove = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerViewMenu = (RecyclerView) rootView.findViewById(R.id.my_recycler_view1);
        recyclerViewGallery = (RecyclerView) rootView.findViewById(R.id.image_layout);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (LogTag.DEBUG) Log.d(TAG, "bundle : " + savedInstanceState);

        mContext = getActivity();
        myDataset = new ArrayList<>();

        //"Above" icon button creator using folder name array.
        for (int i = 0; i < mapSize; i++) {
            myDataset.add(new MyData(folderName.get(i), R.mipmap.ic_launcher));
        }

        recyclerViewAbove.setHasFixedSize(true);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewGallery.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManagerAbove = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);

        recyclerViewAbove.setLayoutManager(linearLayoutManagerAbove);
        recyclerViewMenu.setLayoutManager(linearLayoutManager);
        recyclerViewGallery.setLayoutManager(gridLayoutManager);
        recyclerViewGallery.setItemViewCacheSize(40);

        adapterAbove = new AdapterAbove(myDataset);
        adapterMenu = new MyAdapter(myDataset);

        recyclerViewAbove.setAdapter(adapterAbove);
        recyclerViewMenu.setAdapter(adapterMenu);

        //"Above" icon touch listener.
        recyclerViewAbove.addOnItemTouchListener(new ListenerOnClick(getActivity(), recyclerViewAbove, new ListenerOnClick.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                imageFiles = hashMap.get(keyArray.get(position));

                adapterImage = new AdapterImage(Fragment2.this, imageFiles);
                adapterImage.setOnItemClickListener(new AdapterImage.OnItemClickListener() {


                    @Override
                    public void onClick(int position) {

                    }
                });
                recyclerViewGallery.setAdapter(adapterImage);

            }

            @Override
            public void onItemLongClick(View view, int position) {


            }
        }));
    }


    //폴더생성
    private void createImageGallery() {
        File storageDirectory = Environment.getExternalStoragePublicDirectory("/DCIM");
        mGalleryFolder = new File(storageDirectory, GALLERY_LOCATION);
        if (!mGalleryFolder.exists()) {
            mGalleryFolder.mkdirs();
        }
    }
}
