package com.example.dixitapp;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {

    public String name;
    public String email;
    public int interestCounter;

    public User(String name, String email) {
        this.name = name;
        this.email = email;

        interestCounter = 0;
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

    public int getInterestCounter() {
        return interestCounter;
    }

    public void setInterestCounter(int interestCounter) {
        this.interestCounter = interestCounter;
    }

    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.name);
        result.put("email", this.email);
        result.put("interestCounter", this.interestCounter);

        return result;
    }
}
