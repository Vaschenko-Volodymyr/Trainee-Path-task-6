package com.traineepath.volodymyrvashchenko.trainee_path_task_6.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.traineepath.volodymyrvashchenko.trainee_path_task_6.R;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments.GridViewFragment;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments.ListViewFragment;
import com.traineepath.volodymyrvashchenko.trainee_path_task_6.fragments.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String LIST_FRAGMENT = "list view";
    private static final String GRID_FRAGMENT = "grid view";
    private static final String RECYCLER_FRAGMENT = "recycler view";

    private static final int LIST = 0;
    private static final int GRID = 1;
    private static final int RECYCLER = 2;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, ">> Method: onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragments = new ArrayList<>();
        fillFragments();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Log.v(TAG, "<< Method: onCreate()");
    }

    private void fillFragments() {
        mFragments.add(LIST, new ListViewFragment());
        mFragments.add(GRID, new GridViewFragment());
        mFragments.add(RECYCLER, new RecyclerViewFragment());
    }

    private void setupViewPager(ViewPager viewPager) {
        Log.v(TAG, ">> Method: setupViewPager()");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        addFragmentsToAdapter(adapter);

        viewPager.setAdapter(adapter);
        Log.v(TAG, "<< Method: setupViewPager()");
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private int mCounter = 0;
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.v(TAG, "Inner class: ViewPagerAdapter, Method: getItem()");
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            Log.v(TAG, "Inner class: ViewPagerAdapter, Method: getCount() - invoked " + ++mCounter + " times");

            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            Log.v(TAG, "Inner class: ViewPagerAdapter, Method: addFragment()");
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.v(TAG, "Inner class: ViewPagerAdapter, Method: getPageTitle()");
            return mFragmentTitleList.get(position);
        }
    }

    private void addFragmentsToAdapter(ViewPagerAdapter adapter) {
        adapter.addFragment(mFragments.get(LIST), LIST_FRAGMENT);
        adapter.addFragment(mFragments.get(GRID), GRID_FRAGMENT);
        adapter.addFragment(mFragments.get(RECYCLER), RECYCLER_FRAGMENT);
    }
}