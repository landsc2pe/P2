package com.jayjaylab.lesson.gallery.adapter.frame;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by HOMIN on 2016-06-14.
 */
public class ViewHolderDefault extends RecyclerView.ViewHolder {

    private TextView label;

    public ViewHolderDefault(View v) {
        super(v);
    }

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label1) {
        this.label = label1;
    }
}
