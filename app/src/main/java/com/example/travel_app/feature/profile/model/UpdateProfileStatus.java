package com.example.travel_app.feature.profile.model;

import com.example.travel_app.core.platform.BaseEnum;

public enum UpdateProfileStatus implements BaseEnum {
    UPDATE_PROFILE_SUCCESS(0, "Update profile success"),
    UPDATE_PROFILE_FAIL(1, "Update profile fail"),
    UPDATE_PROFILE_STATUS_WRONG_FORMAT(2, "Please fill to all field");

    private final int status;
    private final String title;

    UpdateProfileStatus(int status, String title) {
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
