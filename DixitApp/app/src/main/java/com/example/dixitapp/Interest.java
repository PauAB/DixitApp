package com.example.dixitapp;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.opencensus.stats.Aggregation;

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

    public void AddCounter()
    {
        Counter++;

        InterestAccess.updateCounter(this.ID, "counter", Counter, new InterestAccess.CounterCallback() {
            @Override
            public void onCallback(int status) {
                if (status == InterestAccess.Constants.STATUS_OK)
                {
                    Log.i("Access", "Added success");
                }
                else if (status == InterestAccess.Constants.STATUS_KO)
                {
                    Log.i("Access", "Added failed");
                }
            }
        });
    }

    public void RemoveCounter()
    {
        if (Counter > 0) Counter--;
        else Counter = 0;

        InterestAccess.updateCounter(this.ID, "counter", Counter, new InterestAccess.CounterCallback() {
            @Override
            public void onCallback(int status) {
                if (status == InterestAccess.Constants.STATUS_OK)
                {
                    Log.i("Access", "Remove success");
                }
                else if (status == InterestAccess.Constants.STATUS_KO)
                {
                    Log.i("Access", "Remove failed");
                }
            }
        });
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