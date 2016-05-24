package com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AsyncImageLoader extends AsyncTask<String, String, Bitmap> {

    private static final String TAG = AsyncImageLoader.class.getSimpleName();

    private ImageView mImage;
    private int mMaxHeight;
    private int mMaxWidth;
    private AsyncTaskCallback mCallback;
    private boolean mDoneWithExceptions = false;


    public AsyncImageLoader(ImageView image, int maxHeight, int maxWidth, AsyncTaskCallback callback) {
        mImage = image;
        mMaxHeight = maxHeight;
        mMaxWidth = maxWidth;
        mCallback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Log.v(TAG, ">> Method: doInBackground()");

        String url = params[0];
        Log.v(TAG, "Method: doInBackground(), key = " + url);

        Bitmap bitmap = Cache.getInstance().getBitmapFromMemCache(url);
        if (bitmap == null) {
            Log.v(TAG, "Method: doInBackground(), Image not in cache");
            bitmap = decodeSampledBitmapFromUrl(url, mMaxHeight, mMaxWidth);
            Cache.getInstance().addBitmapToMemoryCache(url, bitmap);
        }

        Log.v(TAG, "<< Method: doInBackground()");
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap image) {
        Log.v(TAG, ">> Method: onPostExecute()");
        if (isCancelled()) {
            return;
        }
        if (image != null) {
            mImage.setImageBitmap(image);
            if (mDoneWithExceptions) {
                mCallback.showToast(R.string.internet_error);
            }
        }
        Log.v(TAG, "<< Method: onPostExecute()");
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    private Bitmap decodeSampledBitmapFromUrl(String url, int reqHeight, int reqWidth) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;

        return decodeBitmapConsideringOptions(url, options);
    }

    private Bitmap decodeBitmapConsideringOptions(String url, BitmapFactory.Options options) {
        try {
            mDoneWithExceptions = false;
            return BitmapFactory.decodeStream((InputStream) new URL(url).getContent(), null, options);
        } catch (IOException e) {
            mDoneWithExceptions = true;
            return null;
        }
    }
}