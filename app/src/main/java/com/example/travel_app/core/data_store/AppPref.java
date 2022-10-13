package com.example.travel_app.core.data_store;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPref {
    private final Context context;

    public AppPref(Context context) {
        this.context = context;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("AppPref", Context.MODE_PRIVATE);
    }
}
