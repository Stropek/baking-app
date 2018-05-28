package com.example.pscurzytek.bakingapp.services;

import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.utils.JsonConverter;

import org.json.JSONArray;

import java.util.List;

import khttp.KHttp;

public class RecipeService {

    private final String RecipesUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public List<Recipe> getRecipes() {
        JSONArray array = KHttp.get(RecipesUrl).getJsonArray();

        return JsonConverter.convertListTo(array, Recipe.class);
    }

    public Recipe getRecipeById(int id) {
        JSONArray array = KHttp.get(RecipesUrl).getJsonArray();

        List<Recipe> recipes = JsonConverter.convertListTo(array, Recipe.class);

        return recipes.stream().filter(r -> r.getId() == id).findFirst().get();
    }
}
