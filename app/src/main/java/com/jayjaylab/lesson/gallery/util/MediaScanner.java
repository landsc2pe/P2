package com.jayjaylab.lesson.gallery.util;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

/**
 * Created by HOMIN on 2016-06-02.
 */
public class MediaScanner {
    private Context mContext;

    private String mPath;

    private MediaScannerConnection mMediaScanner;
    private MediaScannerConnection.MediaScannerConnectionClient mMediaScannerClient;

    public static MediaScanner newInstance(Context context) {
        return new MediaScanner(context);
    }

    private MediaScanner(Context context) {
        mContext = context;
    }

    public void mediaScanning(final String path) {

        if (mMediaScanner == null) {
            mMediaScannerClient = new MediaScannerConnection.MediaScannerConnectionClient() {

                @Override
                public void onMediaScannerConnected() {
                    mMediaScanner.scanFile(mPath, null); // 디렉토리
                    // 가져옴
                }

                @Override
                public void onScanCompleted(String path, Uri uri) {

                }
            };
            mMediaScanner = new MediaScannerConnection(mContext, mMediaScannerClient);
        }

        mPath = path;
        mMediaScanner.connect();

    }
}