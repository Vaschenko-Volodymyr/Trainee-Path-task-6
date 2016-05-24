package com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.adapters.ListAndGridViewAdapter;

import java.util.ArrayList;

public class GridViewFragment extends BaseFragment {
    private static final String TAG = GridViewFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreateView().");
        View view = inflater.inflate(R.layout.fragment_gridview, container, false);

        GridView grid = (GridView) view.findViewById(R.id.grid_view);

        mData = new ArrayList<>();
        mLoadButton = (Button) view.findViewById(R.id.fragment_button_load);
        final ListAndGridViewAdapter adapter = new ListAndGridViewAdapter(GridViewFragment.this,
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

        grid.setAdapter(adapter);

        Log.v(TAG, "<< Method: onCreateView().");
        return view;
    }
}