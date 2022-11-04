package com.example.travel_app.feature.groups.model;

public class Friend {
    private String uuid;
    private String name;
    private String status;
    private String avatar;

    public Friend(String uuid, String name, String status, String avatar) {
        this.uuid = uuid;
        this.name = name;
        this.status = status;
        this.avatar = avatar;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
