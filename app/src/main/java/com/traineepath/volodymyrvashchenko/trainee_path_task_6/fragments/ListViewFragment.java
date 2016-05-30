package com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.adapters.ListAndGridViewAdapter;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.services.LoaderService;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.utils.AdapterCallback;

import java.util.ArrayList;

public class ListViewFragment extends BaseFragment {
    private static final String TAG = ListViewFragment.class.getSimpleName();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreateView().");
        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        ListView mList = (ListView) view.findViewById(R.id.list_view);

        mData = new ArrayList<>();
        mLoadButton = (Button) view.findViewById(R.id.fragment_button_load);
        final ListAndGridViewAdapter adapter = new ListAndGridViewAdapter(mData, new AdapterCallback() {
                @Override
                public LayoutInflater getLayoutInflater() {
                    return (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }

                @Override
                public Drawable getDrawable(int id) {
                    return getResources().getDrawable(id);
                }

                @Override
                public void startService(String url, int height, int width) {
                    Intent intent = new Intent(getContext(), LoaderService.class);
                    intent.putExtras(LoaderService.getExtras(url, height, width));
                    getActivity().startService(intent);
                }

                @Override
                public void unregisterReceiver(BroadcastReceiver receiver) {
                    getActivity().unregisterReceiver(receiver);
                }

                @Override
                public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
                    getActivity().registerReceiver(receiver, filter);
                }
            }
        );

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestoreListOnStart = true;
                fillModel();
                adapter.notifyDataSetChanged();

            }
        });

        mList.setAdapter(adapter);

        Log.v(TAG, "<< Method: onCreateView().");
        return view;
    }
}