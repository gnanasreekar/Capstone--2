package com.rgs.capstone.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface Dao_news
{
    @Insert
    void insert(NewsTable newsTable);

    @Delete
    void delete(NewsTable newsTable);

    @Query("Select * from fav_news")
    LiveData<List<NewsTable>> getAllNews();

    @Query("Select * from fav_news where title = :title")
    NewsTable checkarti(String title);
}
