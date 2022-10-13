package com.example.travel_app.feature.login.model;

public enum RegisterAccountStatus {
    SUCCESS(1),
    FAIL(0),
    WRONG_FORMAT(2),
    DUPLICATE_ACCOUNT(3);
    RegisterAccountStatus(int status) {}
}
