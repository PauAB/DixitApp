package com.example.dixitapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class InterestRepository {
    private InterestDao interestDao;
    private LiveData<List<InterestEntity>> allInterests;

    InterestRepository(Application application)
    {
        InterestRoomDatabase db = InterestRoomDatabase.getDatabase(application);
        interestDao = db.interestDao();
        allInterests  = interestDao.getAll();
    }

    public LiveData<List<InterestEntity>> getAll() { return allInterests; }

    public void insert(InterestEntity interestEntity)
    {
        new insertAsyncTask(interestDao).execute(interestEntity);
    }

    private static class insertAsyncTask extends AsyncTask<InterestEntity, Void, Void>
    {
        private InterestDao mAsyncTaskDao;
        insertAsyncTask(InterestDao dao) {mAsyncTaskDao = dao;}
        @Override
        protected Void doInBackground(final InterestEntity... params)
        {
            mAsyncTaskDao.insert(
                    params[0].getId(),
                    params[0].getUserImage(),
                    params[0].getUsername(),
                    params[0].getDateAndTime(),
                    params[0].getCategory(),
                    params[0].getText(),
                    params[0].getCounter()
            );
            return null;
        }
    }
}
