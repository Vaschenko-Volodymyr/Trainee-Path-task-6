package com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class AsyncImageLoader extends AsyncTask<String, String, Bitmap> {

    private static final String TAG = AsyncImageLoader.class.getSimpleName();

    private ImageView mImage;
    private int mMaxHeight;
    private int mMaxWidth;

    private final Object mUniqueObject;

    public AsyncImageLoader(ImageView image, int maxHeight, int maxWidth, Object uniqueObject) {
        mImage = image;
        mMaxHeight = maxHeight;
        mMaxWidth = maxWidth;
        mUniqueObject = uniqueObject;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        String urlForLog = loppedUrl(url);
        Log.v(TAG, "Method: doInBackground(), key = " + urlForLog);
        Bitmap bitmap;
        synchronized (mUniqueObject) {
            bitmap = Cache.getInstance().getBitmapFromMemCache(url);
            if (bitmap == null){
                Log.v(TAG, ">> synchronized decodeSampledBitmapFromUrl(), image " +
                        urlForLog +
                        " is not in cache now");
                bitmap = BitmapDecoder.decodeSampledBitmapFromUrl(url, mMaxHeight, mMaxWidth);

            } else {
                bitmap = Cache.getInstance().getBitmapFromMemCache(url);
                Log.v(TAG, ">> synchronized decodeSampledBitmapFromUrl(), image " +
                        urlForLog +
                        " IS in cache now!");
            }
        }
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
        }
        Log.v(TAG, "<< Method: onPostExecute()");
    }

    private String loppedUrl(String url) {
        return url.substring(0, 15) + "..." + url.substring(url.length() - 15, url.length());
    }
}