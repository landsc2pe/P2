package com.jayjaylab.lesson.gallery.adapter.frame;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jayjaylab.lesson.gallery.R;

/**
 * Created by HOMIN on 2016-05-26.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public View rootLayout;

    public ViewHolder(View v) {
        super(v);

        rootLayout = v.findViewById(R.id.rootlayout);
        imageView = (ImageView) v.findViewById(R.id.item_img);
    }
}
