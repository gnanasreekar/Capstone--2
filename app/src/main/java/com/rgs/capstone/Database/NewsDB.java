package com.rgs.capstone.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsTable.class},version = 1,exportSchema = false)
public abstract class NewsDB extends RoomDatabase
{
    public abstract Dao_news dao();
    private static volatile NewsDB INSTANCE;

    static NewsDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsDB.class) {
                INSTANCE = Room
                        .databaseBuilder(context,NewsDB.class,"newsdb")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }
}
