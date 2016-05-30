package com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;

public interface AdapterCallback {

    LayoutInflater getLayoutInflater();

    Drawable getDrawable(int id);

    void startService(String url, int height, int width);

    void unregisterReceiver(BroadcastReceiver receiver);

    void registerReceiver(BroadcastReceiver receiver, IntentFilter filter);
}
