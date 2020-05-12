package com.example.dixitapp;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "interests")
public class InterestEntity {

    @PrimaryKey
    private int id;

    private String UserImage;
    private String Username;
    private String Category;
    private String Text;
    private String DateAndTime;
    private int Counter;

    public InterestEntity() {
        setDateAndTime();
    }

    public InterestEntity(int id, String userImage, String username, String dateAndTime, String category, String text, int counter) {
        this.id = id;
        UserImage = userImage;
        Username = username;
        DateAndTime = dateAndTime;
        Category = category;
        Text = text;
        Counter = counter;

        Counter = 0;
        setDateAndTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDateAndTime() {
        return DateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        DateAndTime = dateAndTime;
    }

    public void setDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm '|' MM.dd.yyyy");
        DateAndTime = sdf.format(new Date());
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public int getCounter() {
        return Counter;
    }

    public void setCounter(int counter) {
        Counter = counter;
    }

    public void addCounter()
    {
        Counter++;
    }

    public void removeCounter()
    {
        if (Counter > 0) Counter--;
        Counter = 0;
    }
}