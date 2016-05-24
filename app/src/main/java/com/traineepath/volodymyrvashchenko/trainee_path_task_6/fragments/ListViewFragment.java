package com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.adapters.ListAndGridViewAdapter;

import java.util.ArrayList;

public class ListViewFragment extends BaseFragment {
    private static final String TAG = ListViewFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreateView().");
        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        ListView mList = (ListView) view.findViewById(R.id.list_view);

        mData = new ArrayList<>();
        mLoadButton = (Button) view.findViewById(R.id.fragment_button_load);
        final ListAndGridViewAdapter adapter = new ListAndGridViewAdapter(ListViewFragment.this,
                mData,
                getActivity().getApplicationContext());
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