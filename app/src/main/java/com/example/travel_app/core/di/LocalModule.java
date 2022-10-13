package com.example.travel_app.core.di;

import android.content.Context;

import com.example.travel_app.core.data_store.AppPref;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {
    @Provides
    @Singleton
    public AppPref provideAppPref(@ApplicationContext Context context) {
        return new AppPref(context);
    }
}
