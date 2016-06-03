package com.jayjaylab.lesson.gallery.util;

import android.util.SparseArray;
import com.jayjaylab.lesson.gallery.util.model.Image;
import com.jayjaylab.lesson.gallery.util.model.Thumbnail;

/**
 * Created by jjkim on 2016. 5. 17..
 */
public interface OnLoadListener {
    void onLoadThumbnails(Thumbnail[] thumbnails);
    void onLoadOriginalImages(SparseArray<Image> images);
}

