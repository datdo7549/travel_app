package com.example.travel_app.feature.model;

import java.util.ArrayList;

public class UserPost {
    private String id;
    private String uid;
    private String actorName;
    private String title;
    private String description;
    private Long createDate;
    private String location;
    private ArrayList<String> likes;
    private ArrayList<CommentModel> comments;

    private boolean isLike = false;

    public UserPost() {}

    public UserPost(String id, String uid, String actorName, String title, String description, Long createDate, String location, ArrayList<String> likes, ArrayList<CommentModel> comments) {
        this.id = id;
        this.uid = uid;
        this.actorName = actorName;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.location = location;
        this.likes = likes;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public ArrayList<CommentModel> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentModel> comments) {
        this.comments = comments;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
