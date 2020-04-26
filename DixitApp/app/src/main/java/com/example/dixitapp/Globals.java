package com.example.dixitapp;

import android.app.Application;

public class Globals extends Application {
    private String password;
    private int animDelay;

    public String getPassword()
    {
        return this.password;
    }
    public int getAnimDelay() { return this.animDelay; }

    public void setPassword(String newPassword)
    {
        this.password = newPassword;
    }
    public void setAnimDelay(int newDelay) { this.animDelay = newDelay; }
}
