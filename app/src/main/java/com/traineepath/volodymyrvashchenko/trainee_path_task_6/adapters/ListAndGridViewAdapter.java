package com.traineepath.volodymyrvashchenko.trainee_path_task_6.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.models.ViewModel;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.services.LoaderService;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.AdapterCallback;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAndGridViewAdapter extends BaseAdapter implements UniqueObject {
    private static final String TAG = ListAndGridViewAdapter.class.getSimpleName();

    private static final String BROADCAST_ACTION = "status";

    private ArrayList mData;
    private LayoutInflater mInflater = null;
    private Map<ViewHolder, BroadcastReceiver> mTasks;
    private AdapterCallback mCallback;

    public ListAndGridViewAdapter(ArrayList data, AdapterCallback callback) {

        mData = data;
        mCallback = callback;
        mInflater = mCallback.getLayoutInflater();
        mTasks = new HashMap<>(data.size());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public ImageView image;
        public TextView text;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.v(TAG, ">> Method - getView()" + position);
        View view = convertView;
        final ViewHolder holder;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_layout, null);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.list_item_image);
            holder.text = (TextView) view.findViewById(R.id.list_item_text);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            mCallback.unregisterReceiver(mTasks.get(holder));
        }

        final ViewModel mModel = (ViewModel) mData.get(position);

        holder.image.setImageDrawable(mCallback.getDrawable(R.drawable.loading));
        holder.text.setText(mModel.getUrl());


        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        BroadcastReceiver receiver = new BroadcastReceiver() {

            private String expectedUrl = mModel.getUrl();
            private ViewHolder viewHolder = holder;

            @Override
            public void onReceive(Context context, Intent intent) {
                if (expectedUrl.equals(intent.getStringExtra(LoaderService.getUrlKey()))) {
                    viewHolder.image.setImageBitmap(Cache.getInstance().getBitmapFromMemCache(
                            intent.getStringExtra(LoaderService.getUrlKey())));
                }
            }
        };

        mTasks.put(holder, receiver);

        mCallback.registerReceiver(receiver, filter);

        Bitmap bitmap = Cache.getInstance().getBitmapFromMemCache(mModel.getUrl());
        if (bitmap == null) {
            mCallback.startService(mModel.getUrl(), holder.image.getHeight(), holder.image.getWidth());
        } else {
            holder.image.setImageBitmap(bitmap);
        }

        return view;
    }
}
