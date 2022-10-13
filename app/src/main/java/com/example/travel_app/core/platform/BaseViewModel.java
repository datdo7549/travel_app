package com.example.travel_app.core.platform;

import androidx.lifecycle.ViewModel;

import com.example.travel_app.core.data_store.AppPref;

import javax.inject.Inject;

public class BaseViewModel extends ViewModel {
    @Inject
    public AppPref appPref;
}
