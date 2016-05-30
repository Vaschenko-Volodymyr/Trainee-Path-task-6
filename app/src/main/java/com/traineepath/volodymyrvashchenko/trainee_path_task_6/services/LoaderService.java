package com.traineepath.volodymyrvashchenko.trainee_path_task_6.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.adapters.UniqueObject;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.BitmapDecoder;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.Cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoaderService extends Service implements UniqueObject {
    private static final String TAG = LoaderService.class.getSimpleName();

    private static final String BROADCAST_ACTION = "status";

    private static final String URL = "url";
    private static final String MAX_HEIGHT = "max_height";
    private static final String MAX_WIDTH = "max_width";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flag, int startId) {
        if (intent == null) return super.onStartCommand(null, flag, startId);

        ExecutorService executor = Executors.newFixedThreadPool(
                getResources().getStringArray(R.array.urls).length);

        Loader loader = new Loader(intent.getStringExtra(URL),
                intent.getIntExtra(MAX_HEIGHT, 0),
                intent.getIntExtra(MAX_WIDTH, 0));

        executor.execute(loader);

        return super.onStartCommand(intent, flag, startId);
    }

    public static Bundle getExtras(String url, int height, int width) {
        Bundle result = new Bundle();

        result.putString(URL, url);
        result.putInt(MAX_HEIGHT, height);
        result.putInt(MAX_WIDTH, width);

        return result;
    }

    private void sendBroadcast(String url) {
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(URL, url);
        sendBroadcast(intent);
    }

    public static String getUrlKey() {
        return URL;
    }

    class Loader implements Runnable {
        private String mLocalTag = TAG + ", inner Class: " + Loader.class.getSimpleName();

        private String mUrl;
        private int mMaxHeight;
        private int mMaxWidth;

        public Loader(String url, int height, int width) {
            mUrl = url;
            mMaxHeight = height;
            mMaxWidth = width;
        }

        @Override
        public void run() {

            String urlForLog = loppedUrl(mUrl);
            Log.v(mLocalTag, "Inner Class: Loader.Method: run(), key = " + urlForLog);

            loadImage();
            sendBroadcast(mUrl);
        }

        private void loadImage() {
            String mLocalTag = TAG + ", inner Class: " +
                    Loader.class.getSimpleName() + ", Method: loadImage";

            String loppedUrl = loppedUrl(mUrl);

            if (Cache.getInstance().getBitmapFromMemCache(mUrl) == null) {
                Log.v(mLocalTag, "Inner Class: Loader.Method: run(), image " +
                        loppedUrl +
                        " is not in cache now");
                BitmapDecoder.decodeSampledBitmapFromUrl(mUrl, mMaxHeight, mMaxWidth);
            }
        }

        private String loppedUrl(String url) {
            Log.v(mLocalTag, "Method: loppedUrl, url = " + url);
            return url.substring(0, 15) + "..." + url.substring(url.length() - 15, url.length());
        }
    }
}
