package com.jayjaylab.lesson.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.frame.ViewHolder;
import com.jayjaylab.lesson.gallery.fragment.FragmentTab1;

/**
 * Created by HOMIN on 2016-05-27.
 */
public class AdapterImageTab1 extends RecyclerView.Adapter<ViewHolder> {
    FragmentTab1 fragment;

    //// TODO: 2016-05-26 add data arg. 
    public AdapterImageTab1(FragmentTab1 fragment ){

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}