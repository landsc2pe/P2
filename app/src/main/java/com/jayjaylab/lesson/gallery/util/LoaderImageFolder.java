package com.jayjaylab.lesson.gallery.util;

import android.app.Activity;
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
import java.util.Set;

/**
 *
 *
 * Created by jjkim on 2016. 5. 21..
 */
public class LoaderImageFolder implements ListenerOnLoad {
    final String TAG = LoaderImageFolder.class.getSimpleName();
    final int LOADER_ID_THUMBNAIL = 0;
    final int LOADER_ID_IMAGE = 1;

    public android.app.LoaderManager.LoaderCallbacks<Cursor> loaderCallbacksForOriginalImages;
    public android.app.LoaderManager.LoaderCallbacks<Cursor> loaderCallbacksForThumbnails;
    public Map<String, List<Image>> map;
    public SparseArray<Image> arrayImage;
    public Thumbnail[] arrayThumbnails;
    OnImageLoadListener onImageLoadListener;


    public void setOnImageLoadListener(OnImageLoadListener listener) {
        onImageLoadListener = listener;
    }

    public void loadImageByMediaStore(final Activity activity) {
        loaderCallbacksForOriginalImages = new android.app.LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                String[] projection = {MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA};

                CursorLoader cursorLoader = new CursorLoader(activity,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, null, null, null);

                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if(LogTag.DEBUG)Log.d(TAG, "count : " + data.getCount());

                int id;
                String path;

                SparseArray<Image> sparseArrayImage = new SparseArray<>(data.getCount());

                data.moveToFirst();
                while (data.moveToNext()) {
                    // making image path to array
                    id = data.getInt(data.getColumnIndex(MediaStore.Images.Media._ID));
                    path = data.getString(data.getColumnIndex(MediaStore.Images.Media.DATA));

                    sparseArrayImage.append(id, new Image(id, path));
                }
                data.close();

                if (sparseArrayImage != null && sparseArrayImage.size() > 0) {
                    onLoadOriginalImages(sparseArrayImage);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        };


        loaderCallbacksForThumbnails = new android.app.LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                if(LogTag.DEBUG)Log.d(TAG, "id : " + id);

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
                if(LogTag.DEBUG)Log.d(TAG, "count : " + data.getCount());

                Thumbnail[] thumbnails = new Thumbnail[data.getCount()];

                String path;
                int id, imageId;
                int count = 0;

                data.moveToFirst();
                while (data.moveToNext()) {
                    //making image path to array
                    path = data.getString(data.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                    id = data.getInt(data.getColumnIndex(MediaStore.Images.Thumbnails._ID));
                    imageId = data.getInt(data.getColumnIndex(MediaStore.Images.Thumbnails
                            .IMAGE_ID));

//                    if(LogTag.DEBUG)Log.d(TAG, "id : " + id + ", imageId : " + imageId + ", path : " + path);
                    thumbnails[count] = new Thumbnail(id, imageId, path);
                    count++;
                }
                data.close();

                if (thumbnails != null && thumbnails.length > 0) {
                    onLoadThumbnails(thumbnails);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        };

        activity.getLoaderManager().initLoader(LOADER_ID_THUMBNAIL, null, loaderCallbacksForThumbnails);
        activity.getLoaderManager().initLoader(LOADER_ID_IMAGE, null, loaderCallbacksForOriginalImages);
    }


    public void loadImageByMediaStore(final Activity activity,
                                      OnImageLoadListener listener) {
        setOnImageLoadListener(listener);
        loadImageByMediaStore(activity);
    }


    // TODO: 2016-05-18 ASK: why does it load Method two times.

    @Override
    public void onLoadThumbnails(Thumbnail[] thumbnails) {
        if(LogTag.DEBUG)Log.d(TAG, "arrayThumbnails : " + thumbnails + ", # : " + thumbnails.length);
        this.arrayThumbnails = thumbnails;
        createMapOfDirectoryImages1(arrayImage, arrayThumbnails);
    }

    @Override
    public void onLoadOriginalImages(SparseArray<Image> sparseArray) {
        if(LogTag.DEBUG)Log.d(TAG, "images : " + sparseArray + ", # : " + sparseArray.size());
        this.arrayImage = sparseArray;
        createMapOfDirectoryImages1(arrayImage, arrayThumbnails);
    }

    synchronized Map<String, List<Image>> createMapOfDirectoryImages1(
            SparseArray<Image> arrayImage, Thumbnail[] thumbnails) {
        if (arrayImage == null || thumbnails == null)
            return null;

        map = new HashMap<>();

        for (Thumbnail thumbnail : thumbnails) {
//            if(LogTag.DEBUG)Log.d(TAG, "thumbnail : " + thumbnail);

            // FIXME: 2016. 5. 17. why is thumbnail null????  => Fixed (Switched row)
            if (thumbnail != null) {
                Image originalImage = arrayImage.get(thumbnail.getImageId());
                if (originalImage != null) {
                    originalImage.setThumbnail(thumbnail);
//                    if(LogTag.DEBUG)Log.d(TAG, "originalImage : " + originalImage);

                    //extract folder path.
                    String parent = new File(originalImage.getPath()).getParent();

                    //mapping
                    if (map.containsKey(parent)) {
                        List<Image> list = map.get(parent);
                        list.add(originalImage);
                    } else {
                        List<Image> list = new ArrayList<>(10);
                        list.add(originalImage);
                        map.put(parent, list);
                    }
                }
            }
        }

        Set<Map.Entry<String, List<Image>>> entrySet = map.entrySet();
        for (Map.Entry entry : entrySet) {
//            if(LogTag.DEBUG)Log.d(TAG, "key : " + entry.getKey() + ", values : " + entry.getValue());
        }

        if(onImageLoadListener != null) {
            onImageLoadListener.onLoad(map);
        }

        return map;
    }

    public interface OnImageLoadListener {
        void onLoad(Map<String, List<Image>> map);
    }
}
