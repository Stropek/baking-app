package com.example.pscurzytek.bakingapp;

import com.example.pscurzytek.bakingapp.activities.RecipesListActivity;
import com.example.pscurzytek.bakingapp.modules.RecipeServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RecipeServiceModule.class} )
public interface AppComponent {

    void inject(RecipesListActivity activity);
}
