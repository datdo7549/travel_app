package com.example.travel_app.feature.model;

public class CommentModel {
    private String id;
    private String userId;
    private String userName;
    private String content;
    private Long createDate;

    public CommentModel() {}

    public CommentModel(String id, String userId, String userName, String content, Long createDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }
}
