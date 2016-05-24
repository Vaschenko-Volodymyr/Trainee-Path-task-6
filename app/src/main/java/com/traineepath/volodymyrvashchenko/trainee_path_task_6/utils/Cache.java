package com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

public class Cache {
    private static final String TAG = Cache.class.getSimpleName();

    public static final int CACHE_SIZE = 4 * 1024 * 1024;

    private static Cache ourInstance = new Cache();
    private static LruCache<String, Bitmap> mMemoryCache;

    private static boolean mCacheIsInitialized = false;

    public static Cache getInstance() {
        if (!mCacheIsInitialized) {
            mMemoryCache = new LruCache<String, Bitmap>(CACHE_SIZE){
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
            mCacheIsInitialized = true;
        }
        return ourInstance;
    }

    private Cache() {
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        Log.v(TAG, "Method: addBitmapToMemoryCache(). key = " + key + " and bitmap = null? : " + bitmap.equals(null) );

        if (getBitmapFromMemCache(key) == null) {

            Log.v(TAG, "Method: addBitmapToMemoryCache(). Bitmap is not in cache now");
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        Log.v(TAG, "Method: getBitmapFromMemCache(). key = " + key);
        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap != null ){
            Log.v(TAG, "Method: getBitmapFromMemCache(). Image is in cache.");
        }
        return bitmap;
    }
}
