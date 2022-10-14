package com.example.travel_app.feature.login.model;

public enum RegisterAccountStatus {
    SUCCESS(1, "Register", "Create account successful"),
    FAIL(0, "Register", "Create account failed"),
    WRONG_FORMAT(2, "Register", "Please insert valid email or password"),
    DUPLICATE_ACCOUNT(3, "Register", "Account exists");

    private int status;
    private String title;
    private String description;
    RegisterAccountStatus(int status, String title, String description) {
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
