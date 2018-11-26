package com.derrick.aad.app.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tv_table")
public class TvEntry {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int tv_id;
    public String original_name;
    public String poster_path;
    public String backdrop_path;

    public TvEntry(int tv_id, String original_name, String poster_path, String backdrop_path) {
        this.tv_id = tv_id;
        this.original_name = original_name;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    @Ignore
    public TvEntry(String original_name, String poster_path, String backdrop_path) {
        this.original_name = original_name;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    @NonNull
    public int getTv_id() {
        return tv_id;
    }

    public void setTv_id(@NonNull int tv_id) {
        this.tv_id = tv_id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


}
