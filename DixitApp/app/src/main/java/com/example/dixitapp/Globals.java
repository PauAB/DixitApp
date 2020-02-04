package com.example.dixitapp;

import android.app.Application;

public class Globals extends Application {
    private String password;

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String newPassword)
    {
        this.password = newPassword;
    }
}
