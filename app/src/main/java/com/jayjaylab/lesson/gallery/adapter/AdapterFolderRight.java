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
 * Created by HOMIN on 2016-04-09.
 */
public class AdapterFolderRight extends RecyclerView.Adapter<AdapterFolderRight.ViewHolder> {
    final String TAG = AdapterFolderRight.class.getSimpleName();
    private ArrayList<IconData> mDataset;

    public AdapterFolderRight(ArrayList<IconData> myDataset) {
        mDataset = new ArrayList<>(myDataset);
    }

    public void addItems(List<IconData> list) {
        Log.d(TAG, "list : " + list + ", mDataset : " + mDataset);
        final int startPosition = mDataset.size();
        mDataset.addAll(list);
        notifyItemRangeInserted(startPosition, list.size());
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).text);
        holder.mImageView.setImageResource(mDataset.get(position).img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.icon_view);
            mTextView = (TextView) view.findViewById(R.id.text_view);
        }
    }
}