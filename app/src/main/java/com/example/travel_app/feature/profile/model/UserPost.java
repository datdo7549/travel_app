package com.example.travel_app.feature.profile.model;

public class UserPost {
    private String postId;
    private String actor;
    private String postTitle;
    private String postCover;
    private String description;

    public UserPost(String postId, String postTitle, String postCover) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postCover = postCover;
    }

    public UserPost(String postTitle, String postCover) {
        this.postTitle = postTitle;
        this.postCover = postCover;
    }

    public UserPost(String postId, String actor, String postTitle, String postCover, String description) {
        this.postId = postId;
        this.actor = actor;
        this.postTitle = postTitle;
        this.postCover = postCover;
        this.description = description;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostCover() {
        return postCover;
    }

    public void setPostCover(String postCover) {
        this.postCover = postCover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
