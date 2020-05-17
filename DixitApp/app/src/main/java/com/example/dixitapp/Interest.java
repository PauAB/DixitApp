package com.example.dixitapp;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Interest {
    public String ID;
    public String UserImage;
    public String Username;
    public String DateAndTime;
    public String Category;
    public String Content;
    public int Counter;

    public Interest(String ID, String userImage, String username, String dateAndTime, String category, String content) {
        this.ID = ID;
        UserImage = userImage;
        Username = username;
        DateAndTime = dateAndTime;
        Category = category;
        Content = content;
        Counter = 0;
    }

    public Interest(Map<String, Object> data) {
        this.ID = data.get("id").toString();
        UserImage = data.get("image").toString();
        Username = data.get("username").toString();
        DateAndTime = data.get("dateAndTime").toString();
        Category = data.get("category").toString();
        Content = data.get("content").toString();
        Counter = (int)(long)data.get("counter");
    }

    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", this.ID);
        result.put("image", this.UserImage);
        result.put("username", this.Username);
        result.put("dateAndTime", this.DateAndTime);
        result.put("category", this.Category);
        result.put("content", this.Content);
        result.put("counter", this.Counter);

        return result;
    }
}