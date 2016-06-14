package com.jayjaylab.lesson.gallery.presenter;

import com.jayjaylab.lesson.gallery.view.ViewMainActivityInterface;

import java.lang.ref.WeakReference;

/**
 * Created by jjkim on 2016. 6. 14..
 */
public class PresenterMainActivity implements PresenterMainActivityInterface {
    WeakReference<ViewMainActivityInterface> view;

    public PresenterMainActivity(ViewMainActivityInterface view) {
        this.view = new WeakReference<ViewMainActivityInterface>(view);
    }

    @Override
    public void loadImage() {

    }

    @Override
    public void checkPermission() {
    }
}
