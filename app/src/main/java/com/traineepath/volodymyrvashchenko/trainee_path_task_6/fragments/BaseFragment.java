package com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.models.ViewModel;

import java.util.ArrayList;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();

    private static final String LOGGED_STATE = "logged";
    private static final int LIST_SIZE = 50;

    protected Button mLoadButton;

    protected ArrayList<ViewModel> mData = new ArrayList<>(LIST_SIZE);
    protected boolean mRestoreListOnStart = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, ">> Method - onCreate()");
        super.onCreate(savedInstanceState);
        mRestoreListOnStart = (savedInstanceState != null) && (savedInstanceState.getBoolean(LOGGED_STATE));

        Log.v(TAG, "<< Method - onCreate()");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v(TAG, ">> Method - onSaveInstanceState()");
        savedInstanceState.putBoolean(LOGGED_STATE, mRestoreListOnStart);
        Log.v(TAG, "<< Method - onSaveInstanceState()");
    }

    @Override
    public void onResume() {
        Log.v(TAG, ">> Method - onResume()");
        super.onResume();
        if (mRestoreListOnStart) {
            Log.v(TAG, "Method: onResume(), Invoke onClick on button");
            mLoadButton.callOnClick();
        }
        Log.v(TAG, "<< Method - onResume()");
    }

    protected void fillModel() {
        Log.v(TAG, ">> Method - fillModel()");
        for (int i = 0; i < LIST_SIZE; i++) {
            String[] urls = getResources().getStringArray(R.array.urls);
            final ViewModel model = new ViewModel(getResources());
            model.setUrl(urls[i%urls.length]);
            mData.add(model);
            Log.v(TAG, "url in model[" + i + "] = " + model.getUrl());
        }

        Log.v(TAG, "<< Method - fillModel()");
    }
}