package com.jayjaylab.lesson.gallery.adapter;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.frame.ViewHolder;
import com.jayjaylab.lesson.gallery.util.model.Image;
import com.jayjaylab.lesson.gallery.util.LogTag;

import java.util.List;

/**
 * Created by HOMIN on 2016-05-27.
 */
public class AdapterImageFolder extends RecyclerView.Adapter<ViewHolder> {
    final String TAG = AdapterImageFolder.class.getSimpleName();
    Fragment fragment;
    private List<Image> imagesFile;
    OnItemClickListener onItemClickListener;

    public AdapterImageFolder(Fragment fragment, List<Image> folderFile) {
        this.fragment = fragment;
        imagesFile = folderFile;
    }

    public void clearItems() {
        imagesFile.clear();
    }

    public void addItems(List<Image> items) {

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            String path = imagesFile.get(position).getThumbnail().getPath();
            if (LogTag.DEBUG) Log.d("ImagePath", path);

            Glide.with(fragment)
                    .load(path)
                    .override(200, 200)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.3f)
                    .centerCrop()
                    .into(holder.imageView);

        } catch (Exception e) {

            String path = imagesFile.get(position).getPath();

            Glide.with(fragment)
                    .load(path)
                    .override(200, 200)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.3f)
                    .centerCrop()
                    .into(holder.imageView);
        }

    }

//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        Image image = imagesFile[position];
//        Log.d(TAG, "onBindViewHolder() : position : " +
//                position +", image: " + image);
//
//        if(image != null) {
//            holder.rootLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(onItemClickListener != null) {
//                        onItemClickListener.onClick(position);
//                    }
//                }
//            });
//            Glide.with(fragment)
//                    .load(image.getThumbnail().getPath()).override(300,300)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .thumbnail(0.3f)
//                    .into(holder.imageView);
//        }
//    }

    @Override
    public int getItemCount() {
        return imagesFile.size();

    }

//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView imageView;
//        public View rootLayout;
//
//        public ViewHolder(View view) {
//            super(view);
//
//            rootLayout = view.findViewById(R.id.rootlayout);
//            imageView = (ImageView) view.findViewById(R.id.item_img);
//        }
//    }

    RequestListener<Uri, GlideDrawable> requestListener =
            new RequestListener<Uri, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, Uri file, Target<GlideDrawable> target,
                                           boolean isFirstResource) {
                    if (LogTag.DEBUG) if (LogTag.DEBUG) Log.d(TAG, ", file : " +
                            file +
                            ", target : " + target + ", isFirstResource : " + isFirstResource);
                    if (LogTag.DEBUG) if (e != null) {
                        e.printStackTrace();
                    }
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable glideDrawable, Uri file,
                                               Target<GlideDrawable> target, boolean isFromMemoryCache,
                                               boolean isFirstResource) {
                    if (LogTag.DEBUG) Log.d(TAG, "onResourceReady() : file : " + file +
                            ", target : " + target +
                            ", isFromMemoryCache : " + isFromMemoryCache +
                            ", isFirstResource : " + isFirstResource);
                    return false;
                }
            };

    public interface OnItemClickListener {
        public void onClick(int position);
    }
}
