package com.jayjaylab.lesson.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayjaylab.lesson.gallery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOMIN on 2016-05-12.
 */
public class AdapterAbove extends RecyclerView.Adapter<AdapterAbove.ViewHolder> {
    final String TAG = AdapterAbove.class.getSimpleName();
    private ArrayList<MyData> mDataset;

    public AdapterAbove(ArrayList<MyData> myDataset) {
        mDataset = new ArrayList<>(myDataset);
    }

    public void addItems(List<MyData> list) {
        Log.d(TAG, "list : " + list + ", mDataset : " + mDataset);
        final int startPosition = mDataset.size();
        mDataset.addAll(list);
        notifyItemRangeInserted(startPosition, list.size());
    }

    @Override
    public AdapterAbove.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view_above, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterAbove.ViewHolder holder, int position) {

        holder.mTextView.setText(mDataset.get(position).text);
        holder.mImageView.setImageResource(mDataset.get(position).img);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image1);
            mTextView = (TextView) view.findViewById(R.id.textview1);
        }
    }
}
