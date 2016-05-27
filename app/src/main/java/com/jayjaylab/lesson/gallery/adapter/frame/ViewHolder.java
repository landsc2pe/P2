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

    public ViewHolder(View view) {
        super(view);

        rootLayout = view.findViewById(R.id.rootlayout);
        imageView = (ImageView) view.findViewById(R.id.item_img);
    }
}
