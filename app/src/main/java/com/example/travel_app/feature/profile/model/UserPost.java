package com.example.travel_app.feature.profile.model;

public class UserPost {
    private String postId;
    private String postTitle;
    private String postCover;

    public UserPost(String postId, String postTitle, String postCover) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postCover = postCover;
    }

    public UserPost(String postTitle, String postCover) {
        this.postTitle = postTitle;
        this.postCover = postCover;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
}
