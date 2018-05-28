package com.example.pscurzytek.bakingapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.services.RecipeService;

import java.util.List;

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {

    private final String TAG = RecipeLoader.class.getName();

    private List<Recipe> recipes = null;
    private RecipeService recipeService;

    public RecipeLoader(Context context, RecipeService recipeService) {
        super(context);
        this.recipeService = recipeService;
    }

    @Override
    public List<Recipe> loadInBackground() {
        try {
            return recipeService.getRecipes();
        }
        catch (Exception ex) {
            Log.e(TAG, "Failed to load recipes");
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        if (recipes == null) {
            forceLoad();
        }
        else {
            deliverResult(recipes);
        }
    }

    @Override
    public void deliverResult(List<Recipe> data) {
        recipes = data;
        super.deliverResult(data);
    }
}
