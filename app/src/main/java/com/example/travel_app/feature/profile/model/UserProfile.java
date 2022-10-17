package com.example.travel_app.feature.profile.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class UserProfile {
    public String uuid;
    public String name;
    public String phone;
    public Long birthday;
    public String address;
    public int likeCount;
    public ArrayList<UserPost> posts = new ArrayList<>();
    public ArrayList<UserPost> favoritePosts = new ArrayList<>();
    public ArrayList<String> friends = new ArrayList<>();
    public ArrayList<String> groups = new ArrayList<>();

    public UserProfile() {}

    public UserProfile(String uuid) {
        this.uuid = uuid;
    }

    public UserProfile(String uuid, String name, String phone, Long birthday, String address) {
        this.uuid = uuid;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.likeCount = 0;
        this.posts = new ArrayList<>();
        this.favoritePosts = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.groups = new ArrayList<>();
    }
}
