package com.example.pscurzytek.bakingapp.services;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RecipeServiceModule {

    @Provides
    @Singleton
    public RecipeService provideRecipeService() {
        return new RecipeService();
    }
}
