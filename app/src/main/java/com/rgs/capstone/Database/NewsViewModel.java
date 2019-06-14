package com.rgs.capstone.Database;



import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepo newsRepo;
    private LiveData<List<NewsTable>> mAllData;


    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepo = new NewsRepo(application);
        mAllData = newsRepo.getAlldata();
    }


    public LiveData<List<NewsTable>> getAllData() {
        return mAllData;
    }

    //To insert moves into DB
    public void insert(NewsTable roomTable) {
        newsRepo.insert(roomTable);
    }

    //To delete moves into DB
    public void delete(NewsTable roomTable) {
        newsRepo.delete(roomTable);
    }

    //
    public NewsTable checkDatabase(String title) {
        return newsRepo.checkDatabase(title);
    }
}
