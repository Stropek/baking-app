package com.example.pscurzytek.bakingapp.widgets;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.models.RecipeBase;
import com.example.pscurzytek.bakingapp.utils.JsonConverter;

import java.util.HashSet;
import java.util.Set;

public class WidgetDataProvider {

    private SharedPreferences sharedPreferences;

    public WidgetDataProvider(Application app) {
        sharedPreferences = app.getSharedPreferences(Constants.SharedPreferences.Name, Context.MODE_PRIVATE);
    }

    public void toggleRecipe(RecipeBase recipe) {
        Set<String> recipes = sharedPreferences.getStringSet(Constants.SharedPreferences.Recipes, null);
        String recipeJson = JsonConverter.convertToJson(recipe);

        if (recipes == null) {
            recipes = new HashSet<>();
        }

        if (recipes.contains(recipeJson)) {
            recipes.remove(recipeJson);
        } else {
            recipes.add(recipeJson);
        }

        sharedPreferences.edit()
                .putStringSet(Constants.SharedPreferences.Recipes, recipes)
                .apply();
    }
}
