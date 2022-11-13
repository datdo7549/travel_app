package com.example.travel_app.feature.login.model;

import com.example.travel_app.core.platform.BaseEnum;

public enum RegisterAccountStatus implements BaseEnum {
    SUCCESS(1, "Register", "Create account successful"),
    FAIL(0, "Register", "Create account failed"),
    WRONG_FORMAT(2, "Register", "Please insert valid email or password"),
    DUPLICATE_ACCOUNT(3, "Register", "Account exists"),
    SUCCESS_BUT_UPDATE_PROFILE_ERROR(4, "Register", "Update profile error"),
    CREATE_POST_SUCCESS(5, "Create  post", "Create post success"),
    CREATE_POST_FAIL(6, "Create  post", "Create post fail");

    private final int status;
    private final String title;
    private final String description;

    RegisterAccountStatus(int status, String title, String description) {
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
