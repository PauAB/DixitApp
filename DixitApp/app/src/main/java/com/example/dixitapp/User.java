package com.example.dixitapp;

import android.util.Log;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public List<Interest> myInterests;

    private String username;

    public User(String name, String email) {
        this.name = name;
        this.email = email;

        myInterests = new ArrayList<>();
        username = this.email.split("@")[0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Interest> getMyInterests() {
        return myInterests;
    }

    public void setMyInterests(List<Interest> myInterests) {
        this.myInterests = myInterests;
    }

    public void addInterest(Interest newInterest)
    {
        this.myInterests.add(newInterest);

        UserAccess.updateInterests(this.username, "interests", this.myInterests, new UserAccess.InterestCallback() {
            @Override
            public void onCallback(int status) {
                if (status == UserAccess.Constants.STATUS_OK)
                {
                    Log.i("Access", "Success");
                }
                else if (status == UserAccess.Constants.STATUS_KO)
                {
                    Log.i("Access", "Failure");
                }
            }
        });
    }

    public void removeInterest(Interest newInterest)
    {
        this.myInterests.remove(newInterest);

        UserAccess.updateInterests(this.username, "interests", this.myInterests, new UserAccess.InterestCallback() {
            @Override
            public void onCallback(int status) {
                if (status == UserAccess.Constants.STATUS_OK)
                {
                    Log.i("Access", "Success");
                }
                else if (status == UserAccess.Constants.STATUS_KO)
                {
                    Log.i("Access", "Failure");
                }
            }
        });
    }

    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.name);
        result.put("email", this.email);
        result.put("interests", this.myInterests);

        return result;
    }
}
