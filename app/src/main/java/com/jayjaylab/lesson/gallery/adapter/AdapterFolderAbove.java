package com.jayjaylab.lesson.gallery.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayjaylab.lesson.gallery.R;
import com.jayjaylab.lesson.gallery.adapter.frame.IconData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOMIN on 2016-05-12.
 */
public class AdapterFolderAbove extends RecyclerView.Adapter<AdapterFolderAbove.ViewHolder> {
    final String TAG = AdapterFolderAbove.class.getSimpleName();
    private ArrayList<IconData> mDataset;

    public AdapterFolderAbove(ArrayList<IconData> myDataset) {
        mDataset = new ArrayList<>(myDataset);
    }

    public void addItems(List<IconData> list) {
        Log.d(TAG, "list : " + list + ", mDataset : " + mDataset);
        final int startPosition = mDataset.size();
        mDataset.addAll(list);
        notifyItemRangeInserted(startPosition, list.size());
    }

    @Override
    public AdapterFolderAbove.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_view_above, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterFolderAbove.ViewHolder holder, int position) {

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
            mImageView = (ImageView) view.findViewById(R.id.icon_view_above);
            mTextView = (TextView) view.findViewById(R.id.text_view_above);
        }
    }
}
