package com.example.dixitapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {InterestEntity.class}, version = 1, exportSchema = false)
public abstract class InterestRoomDatabase extends RoomDatabase {
    public abstract InterestDao interestDao();

    private static volatile InterestRoomDatabase INSTANCE;

    static InterestRoomDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (InterestRoomDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InterestRoomDatabase.class, "interests_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
