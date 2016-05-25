package com.traineepath.volodymyrvashchenko.trainee_path_task_6.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.holders.RecyclerViewHolder;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.models.ViewModel;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.AsyncImageLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>
        implements UniqueObject {

    private List<ViewModel> mData = Collections.emptyList();
    private Context mContext;
    private Map<RecyclerViewHolder, AsyncImageLoader> mTasks;

    public RecyclerViewAdapter(List<ViewModel> data, Context context) {
        mData = data;
        mContext = context;
        mTasks = new HashMap<>(data.size());
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        if (mStatus.get(mData.get(position).getUrl()) == null) {
            mStatus.put(mData.get(position).getUrl(), new Object());
        }

        mTasks.put(holder, new AsyncImageLoader(holder.getImage(),
                holder.getImage().getHeight(),
                holder.getImage().getWidth(),
                mData.get(position)));

        if (mTasks.get(holder).getStatus().equals(AsyncTask.Status.RUNNING)) {
            mTasks.get(holder).cancel(true);
        }
        holder.getImage().setImageDrawable(mContext.getResources().getDrawable(R.drawable.loading));
        holder.getTextView().setText(mData.get(position).getUrl());

        mTasks.get(holder).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mData.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
