package com.example.pscurzytek.bakingapp;

import android.app.Application;

import com.example.pscurzytek.bakingapp.services.RecipeServiceModule;

public class BakingApp extends Application {

    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .recipeServiceModule(new RecipeServiceModule())
                .build();
    }
}
