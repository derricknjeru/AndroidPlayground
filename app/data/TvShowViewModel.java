package com.derrick.aad.app.data;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

public class TvShowViewModel extends AndroidViewModel {
    private static final String LOG_TAG = TvShowViewModel.class.getSimpleName();
    private TvRepository mTvRepository;

    public TvShowViewModel(@NonNull Application application) {
        super(application);
        mTvRepository = new TvRepository(application);
    }

    public void fetchTvShow() {
        mTvRepository.fetchTvShows();
    }

    public LiveData<List<TvEntry>> getTvShowListLiveData() {

        return mTvRepository.getTvShowList();
    }
}
