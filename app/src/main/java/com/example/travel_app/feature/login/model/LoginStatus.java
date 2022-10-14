package com.example.travel_app.feature.login.model;

public enum LoginStatus {
    SUCCESS(0, "Login", "Login success"),
    FAIL(1, "Login", "Wrong email or password"),
    WRONG_FORMAT(2, "Login", "Please insert valid email and password");
    LoginStatus(int status, String title, String description) {
        this.status = status;
        this.title = title;
        this.description = description;
    }

    private final int status;
    private final String title;
    private final String description;

    private int getStatus() {return this.status;}

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }
}
