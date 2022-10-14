package com.example.travel_app.core.extensions;

import android.util.Patterns;

public class StringExtension {
    public static boolean validateRegisterData(String email, String password, String confirmPassword) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() > 8 && password.equals(confirmPassword));
    }

    public static boolean validateLoginData(String email, String password) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() > 8 );
    }
}
