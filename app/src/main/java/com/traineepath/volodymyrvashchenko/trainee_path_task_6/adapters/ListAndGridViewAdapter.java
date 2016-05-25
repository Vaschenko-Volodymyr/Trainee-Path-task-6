package com.traineepath.volodymyrvashchenko.trainee_path_task_6.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments.ListViewFragment;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.models.ViewModel;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.AsyncImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAndGridViewAdapter extends BaseAdapter implements UniqueObject {

    private static final String TAG = ListViewFragment.class.getSimpleName();

    private ArrayList mData;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private Map<ViewHolder, AsyncImageLoader> mTasks;

    public ListAndGridViewAdapter(Fragment fragment, ArrayList data, Context context) {
        mContext = context;
        mData = data;
        mInflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(TAG, ">> Method - getView()" + position);
        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_layout, null);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.list_item_image);
            holder.text = (TextView) view.findViewById(R.id.list_item_text);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            if (mTasks.get(holder).getStatus().equals(AsyncTask.Status.RUNNING)) {
                mTasks.get(holder).cancel(true);
            }
        }

        ViewModel mModel = (ViewModel) mData.get(position);
        if (mStatus.get(mModel.getUrl()) == null) {
            mStatus.put(mModel.getUrl(), new Object());
        }

        mTasks.put(holder, new AsyncImageLoader(holder.image,
                holder.image.getHeight(),
                holder.image.getWidth(),
                mStatus.get(mModel.getUrl())));
        holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.loading));
        holder.text.setText(mModel.getUrl());
        mTasks.get(holder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mModel.getUrl());

        return view;
    }
}
