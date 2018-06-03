package com.example.pscurzytek.bakingapp.widgets;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.utils.JsonConverter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WidgetDataProvider {

    private String TAG = WidgetDataProvider.class.getName();
    private SharedPreferences sharedPreferences;

    public WidgetDataProvider(Application app) {
        sharedPreferences = app.getSharedPreferences(Constants.SharedPreferences.Name, Context.MODE_PRIVATE);
    }

    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        Set<String> recipesSet = sharedPreferences.getStringSet(Constants.SharedPreferences.Recipes, null);

        if (recipesSet != null) {
            for (String recipeJson : recipesSet) {
                try {
                    JSONObject jsonObject = new JSONObject(recipeJson);
                    recipes.add(JsonConverter.convertToType(jsonObject, Recipe.class));
                } catch (Exception ex) {
                    Log.d(TAG, String.format("Issue converting recipeJson to JSONObject: %s", ex.getMessage()));
                }
            }
        }

        return recipes;
    }

    public void toggleRecipe(Context context, Recipe recipe) {
        Set<String> recipesSet = sharedPreferences.getStringSet(Constants.SharedPreferences.Recipes, null);
        String recipeJson = JsonConverter.convertToJson(recipe);

        if (recipesSet == null) {
            recipesSet = new HashSet<>();
        }

        if (recipesSet.contains(recipeJson)) {
            recipesSet.remove(recipeJson);
        } else {
            recipesSet.add(recipeJson);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.SharedPreferences.Recipes).apply();
        editor.putStringSet(Constants.SharedPreferences.Recipes, recipesSet).apply();

        IngredientWidgetService.startActionUpdateRecipesWidgets(context);
    }

    public boolean isPersisted(Recipe recipe) {
        Set<String> recipes = sharedPreferences.getStringSet(Constants.SharedPreferences.Recipes, null);
        return recipes != null && recipes.contains(JsonConverter.convertToJson(recipe));
    }
}
