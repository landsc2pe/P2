package com.jayjaylab.lesson.gallery.util;

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
@Config(constants = BuildConfig.class)
public class LoaderImageFolderTest  {
    MainActivity mainActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void testIfImageThumbnailsReturnsWithin5econds() {
        LoaderImageFolder loaderImageFolder = new LoaderImageFolder();
        loaderImageFolder.loadImageByMediaStore(mainActivity);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {

        }

        assertThat(loaderImageFolder.arrayImage, is(notNullValue()));
        assertThat(loaderImageFolder.arrayThumbnails, is(notNullValue()));
    }
}
