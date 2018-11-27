package com.derrick.aad.app.data;

import java.util.List;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface TvDao {
    // @Query("SELECT * FROM tv_table")
    //LiveData<List<TvEntry>> getTvShows();

    @Query("SELECT * FROM tv_table")
    DataSource.Factory<Integer, TvEntry> getTvShows();

    @Insert
    void bulkInsert(List<TvEntry> tvEntries);

    @Query("DELETE FROM tv_table")
    void deleteAll();
}
