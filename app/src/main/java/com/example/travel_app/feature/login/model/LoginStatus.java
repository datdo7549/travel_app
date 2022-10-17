package com.example.travel_app.feature.login.model;

import com.example.travel_app.core.platform.BaseEnum;

public enum LoginStatus implements BaseEnum {
    SUCCESS(0, "Login", "Login success"),
    FAIL(1, "Login", "Wrong email or password"),
    WRONG_FORMAT(2, "Login", "Please insert valid email and password"),
    GOOGLE_SIGN_IN_AUTO_LOG_IN(3, "Google login", "Google sign in auto login"),
    GOOGLE_SIGN_IN_SUCCESS(4, "Google login", "Google sign in success");

    LoginStatus(int status, String title, String description) {
        this.status = status;
        this.title = title;
        this.description = description;
    }

    private final int status;
    private final String title;
    private final String description;

    @Override
    public String getDescription() {
        return this.description;
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
