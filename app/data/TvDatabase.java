package com.derrick.aad.app.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = TvEntry.class, version = 1)
public abstract class TvDatabase extends RoomDatabase {

    private static String DATABASE_NAME = "tv_database";
    private static TvDatabase sInstance = null;


    public static TvDatabase getsInstance(Context mContext) {
        if (sInstance == null) {
            synchronized (TvDatabase.class) {
                sInstance = Room.databaseBuilder(mContext.getApplicationContext(), TvDatabase.class, DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract TvDao tvDao();

}
