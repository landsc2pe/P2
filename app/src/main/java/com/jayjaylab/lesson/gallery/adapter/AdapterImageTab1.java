package com.jayjaylab.lesson.gallery.adapter;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.frame.ViewHolder;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab1;
import com.jayjaylab.lesson.gallery.util.LogTag;
import com.jayjaylab.lesson.gallery.util.model.Image;
import com.jayjaylab.lesson.gallery.util.model.Thumbnail;

import java.util.List;

/**
 * Created by HOMIN on 2016-05-27.
 */
public class AdapterImageTab1 extends RecyclerView.Adapter<ViewHolder> {
    FragmentTab1 fragment;
    // TODO: 2016. 6. 7.
    List<Image> images;
    // FIXME: 2016. 6. 7. to be removed...
    List<Thumbnail> imagesFile;

    // TODO : get map!
    public AdapterImageTab1(FragmentTab1 fragment, List<Thumbnail> thumbnails){
        this.fragment = fragment;
        imagesFile = thumbnails;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(imagesFile != null) {
            String path = imagesFile.get(position).getPath();
            if (LogTag.DEBUG) Log.d("ImagePath", path);

            Glide.with(fragment)
                    .load(path)
                    .override(200, 200)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.3f)
                    .centerCrop()
                    .into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return imagesFile.size();
    }
}