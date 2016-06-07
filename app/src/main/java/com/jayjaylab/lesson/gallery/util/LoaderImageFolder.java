package com.jayjaylab.lesson.gallery.util;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;

import com.jayjaylab.lesson.gallery.util.model.Image;
import com.jayjaylab.lesson.gallery.util.model.Thumbnail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jjkim on 2016. 5. 21..
 */
public class LoaderImageFolder implements OnLoadListener {
    final String TAG = LoaderImageFolder.class.getSimpleName();
    final int LOADER_ID_THUMBNAIL = 0;
    final int LOADER_ID_IMAGE = 1;


    public static Map<String, List<Image>> map;
    SparseArray<Thumbnail> arrayThumbnails;
    SparseArray<Image> arrayImage;
    ArrayList<Integer> sparseKeys;


    Context context;
    OnImageLoadListener onImageLoadListener;
    android.app.LoaderManager.LoaderCallbacks<Cursor> loaderCallbacksForOriginalImages;
    android.app.LoaderManager.LoaderCallbacks<Cursor> loaderCallbacksForThumbnails;


    public interface OnImageLoadListener {
        void onLoad(Map<String, List<Image>> map, SparseArray<Thumbnail> thumbnails, ArrayList<Integer> sparseKeys);
    }


    public void setOnImageLoadListener(OnImageLoadListener listener) {
        onImageLoadListener = listener;

    }


    public void imageLoaderByMediaStore(final Activity activity, OnImageLoadListener listener, Context context) {
        loadImageByMediaStore(activity);
        setOnImageLoadListener(listener);
        this.context = context;

    }

    public void loadImageByMediaStore(final Activity activity) {
        Log.d(TAG, "loadImageByMediaStore() : activity : " + activity);


        loaderCallbacksForOriginalImages = new android.app.LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                if (LogTag.DEBUG) Log.d(TAG, "id1 : " + id);

                String[] projection = {MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA};

                CursorLoader cursorLoader = new CursorLoader(activity,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);

                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                int id;
                String path;
                if (data != null && data.getCount() > 0) {
                    if (LogTag.DEBUG) Log.d(TAG, "count : " + data.getCount());

                    sparseKeys = new ArrayList();
                    SparseArray<Image> sparseArrayImage = new SparseArray<>(data.getCount());


                    data.moveToFirst();
                    while (data.moveToNext()) {
                        // making image path to array
                        id = data.getInt(data.getColumnIndex(MediaStore.Images.Media._ID));
                        path = data.getString(data.getColumnIndex(MediaStore.Images.Media.DATA));


                        sparseArrayImage.append(id, new Image(id, path));
                        sparseKeys.add(id);


                    }
                    if (LogTag.DEBUG) Log.d("SparseArray", " count : " + sparseKeys.size());
                    for (int i = 0; i < sparseKeys.size(); i++)
                        if (LogTag.DEBUG) Log.d("SparseArray", " key: " + sparseKeys.get(i));

                    if (sparseArrayImage.size() > 0) {
                        onLoadOriginalImages(sparseArrayImage);
                    }
                }


            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                loader = null;

            }
        };


        loaderCallbacksForThumbnails = new android.app.LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                if (LogTag.DEBUG) Log.d(TAG, "id2 : " + id);

                String[] projection = {MediaStore.Images.Thumbnails._ID,
                        MediaStore.Images.Thumbnails.DATA,
                        MediaStore.Images.Thumbnails.IMAGE_ID};

                CursorLoader cursorLoader = new CursorLoader(activity,
                        MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);

                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                String path;
                int id, imageId;

                if (data != null && data.getCount() > 0) {
                    if (LogTag.DEBUG) Log.d(TAG, "count : " + data.getCount());

                    SparseArray<Thumbnail> thumbnails = new SparseArray<>();

                    data.moveToFirst();
                    while (data.moveToNext()) {
                        //making image path to array
                        path = data.getString(data.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                        id = data.getInt(data.getColumnIndex(MediaStore.Images.Thumbnails._ID));
                        imageId = data.getInt(data.getColumnIndex(MediaStore.Images.Thumbnails
                                .IMAGE_ID));

//                    if(LogTag.DEBUG)Log.d(TAG, "id : " + id + ", imageId : " + imageId + ", path : " + path);

                        thumbnails.append(imageId, new Thumbnail(id, imageId, path));
                    }

                    if (thumbnails.size() > 0) {
                        onLoadThumbnails(thumbnails);
                    }
                }

            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                loader = null;

            }
        };


        activity.getLoaderManager().initLoader(LOADER_ID_IMAGE, null, loaderCallbacksForOriginalImages);
        activity.getLoaderManager().initLoader(LOADER_ID_THUMBNAIL, null, loaderCallbacksForThumbnails);


    }


    // TODO: 2016-05-18 ASK: why does it load Method two times.

    @Override
    public void onLoadThumbnails(SparseArray<Thumbnail> thumbnails) {
        if (LogTag.DEBUG)
            Log.d(TAG, "arrayThumbnails : " + thumbnails + ", # : " + thumbnails.size());
        if (LogTag.DEBUG) Log.d(TAG, "thread1 : " + Thread.currentThread());
        arrayThumbnails = thumbnails;
        MapData(arrayImage, arrayThumbnails);
    }

    @Override
    public void onLoadOriginalImages(SparseArray<Image> sparseArray) {
        if (LogTag.DEBUG) Log.d(TAG, "images : " + sparseArray + ", # : " + sparseArray.size());
        if (LogTag.DEBUG) Log.d(TAG, "thread2 : " + Thread.currentThread());
        arrayImage = sparseArray;
//
//        if(LogTag.DEBUG)Log.d("SparseArray", ""+arrayImage.size());
//        if(LogTag.DEBUG) for(int i=0; i<sparseArray.size(); i++)Log.d("SparseArray", arrayImage.valueAt(i).getPath());

        MapData(arrayImage, arrayThumbnails);
    }

    Map<String, List<Image>> MapData(
            SparseArray<Image> arrayImage, SparseArray<Thumbnail> thumbnails) {


        if (arrayImage == null || thumbnails == null) {
            return null;
        }

        map = new HashMap<>();

        for (Integer keys : sparseKeys) {
            Image originalImage = arrayImage.get(keys);
            Thumbnail thumbnail = thumbnails.get(keys);

            originalImage.setThumbnail(thumbnail);

            String parent = new File(originalImage.getPath()).getParent();

            if (map.containsKey(parent)) {
                List<Image> list = map.get(parent);
                list.add(originalImage);
            } else {
                List<Image> list = new ArrayList<>();
                list.add(originalImage);
                map.put(parent, list);
            }

        }


//
//        for (Thumbnail thumbnail : thumbnails) {
////            if(LogTag.DEBUG)Log.d(TAG, "thumbnail : " + thumbnail);
//
//            // FIXME: 2016. 5. 17. why is thumbnail null????  => Fixed (Switched row)
//            if (thumbnail != null) {
//                Image originalImage = arrayImage.get(thumbnail.getImageId());
//                if (originalImage != null) {
//                    originalImage.setThumbnail(thumbnail);
////                    if(LogTag.DEBUG)Log.d(TAG, "originalImage : " + originalImage);
//
//                    //extract folder path.
//                    String parent = new File(originalImage.getPath()).getParent();
//
//                    //mapping
//                    if (map.containsKey(parent)) {
//                        List<Image> list = map.get(parent);
//                        list.add(originalImage);
//                    } else {
//                        List<Image> list = new ArrayList<>(10);
//                        list.add(originalImage);
//                        map.put(parent, list);
//                    }
//                }
//            }
//        }

        if (onImageLoadListener != null) {
            onImageLoadListener.onLoad(map, thumbnails, sparseKeys);
        }


        return map;


    }
}
