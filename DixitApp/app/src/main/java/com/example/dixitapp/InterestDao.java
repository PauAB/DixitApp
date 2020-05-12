package com.example.dixitapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface InterestDao {
    @Query("INSERT OR IGNORE INTO interests VALUES(:id, :image, :username, :dateAndTime, :category, :text, :counter)")
    void insert(int id, String image, String username, String dateAndTime, String category, String text, int counter);

    @Query("SELECT * FROM interests ORDER BY id ASC")
    LiveData<List<InterestEntity>> getAll();
}
