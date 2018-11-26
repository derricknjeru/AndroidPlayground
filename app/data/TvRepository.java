package com.derrick.aad.app.data;

import android.app.Application;
import android.util.Log;

import com.derrick.aad.app.utils.AppExecutors;
import com.derrick.aad.app.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TvRepository {
    private static final String LOG_TAG = TvRepository.class.getSimpleName();
    private TvDao mTvDao;


    private LiveData<List<TvEntry>> mTvShowList;


    public TvRepository(Application mApplication) {
        TvDatabase tvDatabase = TvDatabase.getsInstance(mApplication);
        mTvDao = tvDatabase.tvDao();

        /*mTvShowList = new LivePagedListBuilder<>(
                mTvDao.getTvShows(), *//* page size *//* 5).build();*/


        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(10).build();


        //mTvShowList = new LivePagedListBuilder<>(mTvDao.getTvShows(), pagedListConfig).build();

        mTvShowList = mTvDao.getTvShows();

    }

    public LiveData<List<TvEntry>> getTvShowList() {
        Log.d(LOG_TAG, "@Total xx::" + mTvShowList);
        return mTvShowList;
    }

    public void fetchTvShows() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    saveTvMovies(fetchMovies());
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "@tvResult JSONException::" + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d(LOG_TAG, "@tvResult IOException::" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }


    private void saveTvMovies(String response) throws JSONException {
        if (response != null && !response.isEmpty()) {

            List<TvEntry> tvEntryList = new ArrayList<>();

            JSONObject object = new JSONObject(response);

            JSONArray jsonArray = object.getJSONArray("results");

            Log.d(LOG_TAG, "@tvResult jsonArray::" + jsonArray);


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject tvShow = jsonArray.getJSONObject(i);

                String original_name = tvShow.getString("original_name");
                String poster_path = tvShow.getString("poster_path");
                String backdrop_path = tvShow.getString("backdrop_path");

                TvEntry tvEntry = new TvEntry(original_name, poster_path, backdrop_path);
                tvEntryList.add(tvEntry);

            }

            Log.d(LOG_TAG, "@tvResult fetched list::" + tvEntryList);
            if (tvEntryList != null && tvEntryList.size() > 0) {
                mTvDao.deleteAll();
                mTvDao.bulkInsert(tvEntryList);
            }

        }
    }

    private String fetchMovies() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.POPULAR_TV_URL)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
