package com.example.travel_app.feature.profile.model;

import com.example.travel_app.core.platform.BaseEnum;

public enum UpdateProfileStatus implements BaseEnum {
    UPDATE_PROFILE_SUCCESS(0, "Profile", "Update profile success"),
    UPDATE_PROFILE_FAIL(1, "Profile", "Update profile fail"),
    UPDATE_PROFILE_STATUS_WRONG_FORMAT(2, "Profile", "Please fill to all field");

    private final int status;
    private final String title;
    private final String description;


    UpdateProfileStatus(int status, String title, String description) {
        this.status = status;
        this.title = title;
        this.description = description;
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
        return this.description;
    }
}
