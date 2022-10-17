package com.example.travel_app.feature.profile.model;

import com.example.travel_app.core.platform.BaseEnum;

public enum UserState implements BaseEnum {
    USER_LOG_IN(0, "User login"),
    USER_LOG_OUT(1, "User log out");

    private final int status;
    private final String title;

    UserState(int status, String title) {
        this.status = status;
        this.title = title;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
