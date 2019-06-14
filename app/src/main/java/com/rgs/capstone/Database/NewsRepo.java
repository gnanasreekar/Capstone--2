package com.rgs.capstone.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

class NewsRepo
{
    private Dao_news dao;
    private LiveData<List<NewsTable>> alldata;

    NewsRepo(Application application){
        NewsDB db = NewsDB.getDatabase(application);
        dao = db.dao();
        alldata = dao.getAllNews();
    }

    LiveData<List<NewsTable>> getAlldata()
    {
        return alldata;
    }

    void insert(NewsTable newsTable){
        new insertDataTask(dao).execute(newsTable);
    }

    public static class insertDataTask extends AsyncTask<NewsTable,Void,Void> {

        private Dao_news dao;
        insertDataTask(Dao_news movieDao){
            this.dao = movieDao;
        }
        @Override
        protected Void doInBackground(NewsTable... roomTables) {
            dao.insert(roomTables[0]);
            return null;
        }
    }

    void delete(NewsTable newsTable){
        new deleteDataTask(dao).execute(newsTable);
    }

    public static class deleteDataTask extends AsyncTask<NewsTable,Void,Void>{

        private Dao_news dao;
        deleteDataTask(Dao_news movieDao){
            this.dao = movieDao;
        }
        @Override
        protected Void doInBackground(NewsTable... roomTables) {
            dao.delete(roomTables[0]);
            return null;
        }
    }

     NewsTable checkDatabase(String title){
        return dao.checkarti(title);
    }
}
