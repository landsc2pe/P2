package com.jayjaylab.lesson.gallery.util;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import com.jayjaylab.lesson.gallery.BuildConfig;
import com.jayjaylab.lesson.gallery.activity.MainActivity;
import com.jayjaylab.lesson.gallery.util.model.Thumbnail;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by jjkim on 2016. 7. 6..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = android.os.Build.VERSION_CODES.LOLLIPOP)
public class LoaderImageFolderTest  {
    AppCompatActivity activity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // can get Application object
        activity = Robolectric.buildActivity(AppCompatActivity.class).create().get();
    }

    @Test
    public void testIfImageThumbnailsReturnsWithin5econds() {
        LoaderImageFolder loaderImageFolder = new LoaderImageFolder();
        loaderImageFolder.loadImageByMediaStore(activity);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }

        assertThat(loaderImageFolder.arrayImage, is(notNullValue()));
        assertThat(loaderImageFolder.arrayThumbnails, is(notNullValue()));
    }
}
