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
import android.widget.Toast;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments.ListViewFragment;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.models.ViewModel;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.AsyncImageLoader;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.AsyncTaskCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListAndGridViewAdapter extends BaseAdapter {

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

        mTasks.put(holder, new AsyncImageLoader(holder.image,
                holder.image.getHeight(),
                holder.image.getWidth(),
                new AsyncTaskCallback() {
                    public void showToast(int textId) {
                        Toast.makeText(mContext, textId, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public String getString(int urlId) {
                        return mContext.getResources().getString(urlId);
                    }
                }));

        ViewModel mModel = (ViewModel) mData.get(position);
        holder.text.setText(mModel.getUrl());
        mTasks.get(holder).execute(mModel.getUrl());

        return view;
    }
}
