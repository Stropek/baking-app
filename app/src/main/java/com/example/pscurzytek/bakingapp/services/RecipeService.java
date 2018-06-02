package com.example.pscurzytek.bakingapp.services;

import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.utils.JsonConverter;

import org.json.JSONArray;

import java.util.List;

import khttp.KHttp;

public class RecipeService {

    public List<Recipe> getRecipes() {
        String recipesUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        JSONArray array = KHttp.get(recipesUrl).getJsonArray();

        return JsonConverter.convertArrayToType(array, Recipe.class);
    }
}
