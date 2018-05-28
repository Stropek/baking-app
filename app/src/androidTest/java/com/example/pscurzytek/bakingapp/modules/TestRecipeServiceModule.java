package com.example.pscurzytek.bakingapp.modules;

import com.example.pscurzytek.bakingapp.services.RecipeService;

import org.mockito.Mockito;

public class TestRecipeServiceModule extends RecipeServiceModule {

    @Override
    public RecipeService provideRecipeService() {
        return Mockito.mock(RecipeService.class);
    }
}
