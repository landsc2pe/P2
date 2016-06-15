package com.jayjaylab.lesson.gallery.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.AdapterFolderAbove;
import com.jayjaylab.lesson.gallery.adapter.AdapterFolderRight;
import com.jayjaylab.lesson.gallery.adapter.AdapterImageFolder;
import com.jayjaylab.lesson.gallery.adapter.frame.IconData;
import com.jayjaylab.lesson.gallery.util.LogTag;
import com.jayjaylab.lesson.gallery.util.OnClickListener;
import com.jayjaylab.lesson.gallery.util.model.Image;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Homin on 2016-04-07.
 */
public class FragmentFolder extends Fragment {

    final String TAG = FragmentFolder.class.getSimpleName();
    public static String KEY_IMAGE = "image";


    Activity mActivity;

    int mapSize;
    Map<String, List<Image>> hashMap;
    List<String> folderName;
    List<String> keyArray;
    List<Image> imageFiles;


    private RecyclerView recyclerViewAbove;
    private RecyclerView recyclerViewMenu;
    private RecyclerView recyclerViewGallery;
    private AdapterFolderRight adapterMenu;
    private AdapterFolderAbove adapterFolderAbove;
    private ArrayList<IconData> myDataset;
    private File mGalleryFolder;
    private String GALLERY_LOCATION = ".thumbnails";
    private AdapterImageFolder[] adapterImageFolder;
    private AdapterImageFolder adapterImageFolder1;

    public static FragmentFolder newInstance(Map<String, List<Image>> map) {
        Bundle args = new Bundle();

        Parcelable mapParcelable = Parcels.wrap(map);
        args.putParcelable("map", mapParcelable);
        FragmentFolder fragmentFolder = new FragmentFolder();
        fragmentFolder.setArguments(args);
        return fragmentFolder;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        createImageGallery();

        View rootView = inflater.inflate(R.layout.layout_fragment_folder, container, false);
        recyclerViewAbove = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerViewMenu = (RecyclerView) rootView.findViewById(R.id.my_recycler_view1);
        recyclerViewGallery = (RecyclerView) rootView.findViewById(R.id.image_layout);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (LogTag.DEBUG) Log.d(TAG, "bundle : " + savedInstanceState);

        mActivity = getActivity();
        myDataset = new ArrayList<>();

        hashMap = Parcels.unwrap(getArguments().getParcelable("map"));
        setIconAndImageMap(hashMap);
//        loaderImageFolder = new LoaderImageFolder();

//        loaderImageFolder.setOnImageLoadListener(new LoaderImageFolder.OnImageLoadListener() {
//            @Override
//            public void onLoad(Map<String, List<Image>> map) {
//
//            }
//        });
//        loaderImageFolder.loadImageByMediaStore(getApplicationContext());

//        loaderImageFolder.imageLoaderByMediaStore(mActivity, new LoaderImageFolder.OnImageLoadListener() {
//            @Override
//            public void onLoad(Map<String, List<Image>> map) {
//                if(LogTag.DEBUG) Log.d(TAG, "map : " + map);
//
//                // display given images on gallery
//                setIconAndImageMap(map);
//            }
//        });

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

        if (LogTag.DEBUG) Log.d(TAG, "myDataset : " + myDataset);
        adapterFolderAbove = new AdapterFolderAbove(myDataset);
        adapterMenu = new AdapterFolderRight(myDataset);

        recyclerViewAbove.setAdapter(adapterFolderAbove);
        recyclerViewMenu.setAdapter(adapterMenu);


        //"Above" icon touch listener.
        recyclerViewAbove.addOnItemTouchListener(new OnClickListener(getActivity(), recyclerViewAbove, new OnClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                // FIXME: 2016. 5. 24. WTF
                imageFiles = hashMap.get(keyArray.get(position));
                if (adapterImageFolder[position] == null) {
                    adapterImageFolder1 = new AdapterImageFolder(FragmentFolder.this, imageFiles);
                    adapterImageFolder[position] = adapterImageFolder1;

                    if (LogTag.DEBUG) Log.d("IconClick", "Create");
                }
                // FIXME: 2016. 6. 14. 무슨 문제 있을까나? 괜찮을 까나.
                recyclerViewGallery.setAdapter(adapterImageFolder[position]);
                if (LogTag.DEBUG) Log.d("IconClick", "Use");

//                adapterImageFolder.setOnItemClickListener(new AdapterImageFolder.OnItemClickListener() {
//                    @Override
//                    public void onClick(int position) {
//
//                    }
//                });
            }

            @Override
            public void onItemLongClick(View view, int position) {


            }
        }));
    }


    public void sortImagePath(Map<String, List<Image>> map) {
        mapSize = map.size();
        adapterImageFolder = new AdapterImageFolder[mapSize];
        Log.d(TAG, "mapSize : " + mapSize + ", hashMap : " + map);

        folderName = new ArrayList<>();
        keyArray = new ArrayList<>();

        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String keys = iter.next();
            keyArray.add(keys);
            String[] it = keys.split("/");
            int a = it.length - 1;
            folderName.add(it[a]);
        }

//        folderName = hashMap.keySet().toArray(new String[map.size()]);
    }


    /**
     * 아이콘 세팅 및 해쉬맵 설정한다.
     *
     * @param map 이미지를 가지는 맵.
     */
    void setIconAndImageMap(Map<String, List<Image>> map) {
        sortImagePath(map);
        //"Above" icon button creator using folder name array.
        for (int i = 0; i < mapSize; i++) {
            if (LogTag.DEBUG) Log.d(TAG, "folder : " + folderName.get(i));
            myDataset.add(new IconData(folderName.get(i), R.mipmap.ic_launcher));
        }
        if (LogTag.DEBUG) Log.d(TAG, "myDataset : " + myDataset.toString());
//        adapterFolderAbove.addItems(myDataset);
//        adapterMenu.addItems(myDataset);
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
