package com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BitmapDecoder {
    private static final String TAG = BitmapDecoder.class.getSimpleName();

    public static Bitmap decodeSampledBitmapFromUrl(String url, int reqHeight, int reqWidth) {
        Log.v(TAG, ">> decodeSampledBitmapFromUrl()");

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = decodeBitmapConsideringOptions(url, options);
        Cache.getInstance().addBitmapToMemoryCache(url, bitmap);

        Log.v(TAG, "<< decodeSampledBitmapFromUrl()");
        return bitmap;
    }

    private static Bitmap decodeBitmapConsideringOptions(String url, BitmapFactory.Options options) {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(url).getContent(), null, options);
        } catch (IOException e) {
            return null;
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight && width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
