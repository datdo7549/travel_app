package com.example.travel_app.feature.explore.model;

public class Comment {
    private String id;
    private String postId;
    private String user_avatar;
    private String comment;
    private Long date;

    public Comment(String id, String postId, String user_avatar, String comment, Long date) {
        this.id = id;
        this.postId = postId;
        this.user_avatar = user_avatar;
        this.comment = comment;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
