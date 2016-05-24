package com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.adapters.RecyclerViewAdapter;

public class RecyclerViewFragment extends BaseFragment {
    private static final String TAG = RecyclerViewFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreateView().");
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recycler_view);

        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(mData, getContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLoadButton = (Button) view.findViewById(R.id.fragment_button_load);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRestoreListOnStart = true;
                fillModel();
                adapter.notifyDataSetChanged();

            }
        });

        Log.v(TAG, "<< Method: onCreateView().");
        return view;
    }
}