package com.derrick.aad.app;

import android.os.Bundle;
import android.util.Log;

import com.derrick.aad.R;
import com.derrick.aad.app.data.TvAdapter;
import com.derrick.aad.app.data.TvEntry;
import com.derrick.aad.app.data.TvShowViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {


    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    TvShowViewModel tvShowViewModel;
    private RecyclerView mRecyclerView;
    private TvAdapter mTvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecyclerView = findViewById(R.id.recycler_list);
        mTvAdapter = new TvAdapter();
        mRecyclerView.setAdapter(mTvAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);

        tvShowViewModel.getTvShowListLiveData().observe(this, tvEntries -> mTvAdapter.submitList(tvEntries));
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvShowViewModel.fetchTvShow();
    }
}
