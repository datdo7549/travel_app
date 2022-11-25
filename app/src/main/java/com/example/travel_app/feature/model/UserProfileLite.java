package com.example.travel_app.feature.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class UserProfileLite {
    public String uuid = "";
    public String name = "";
    public String email = "";

    public UserProfileLite() {}

    public UserProfileLite(String uuid) {
        this.uuid = uuid;
    }

    public UserProfileLite(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UserProfileLite(String uuid, String name, String email) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
    }
}
