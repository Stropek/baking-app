package com.example.pscurzytek.bakingapp.utils;

import com.example.pscurzytek.bakingapp.MockData;
import com.example.pscurzytek.bakingapp.models.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class JsonConverterTests {

    @Test
    public void convertToRecipe_nullJsonObject_returnsNull() {
        // when
        Recipe result = JsonConverter.convertToType(null, Recipe.class);

        // then
        assertEquals(null, result);
    }

    @Test
    public void convertToRecipe_validJsonObject_returnsNull() throws JSONException {
        // given
        String json = MockData.SingleRecipe;
        JSONObject jsonObject = new JSONObject(json);

        // when
        Recipe result = JsonConverter.convertToType(jsonObject, Recipe.class);

        // then
        assertEquals(1, result.getId());
        assertEquals("recipe name", result.getName());
        assertEquals(0, result.getIngredients().size());
        assertEquals(0, result.getSteps().size());
        assertEquals(2, result.getServings());
        assertEquals("image path", result.getImage());
    }

    @Test
    public void convertToRecipes_nullJsonObject_returnsNull() {
        // when
        List<Recipe> result = JsonConverter.convertArrayToType(null, Recipe.class);

        // then
        assertEquals(null, result);
    }

    @Test
    public void convertToRecipes_validJsonObject_returnsListOfRecipes() throws JSONException {
        // given
        String json = MockData.ListOfRecipes;
        JSONArray jsonObject = new JSONArray(json);

        // when
        List<Recipe> result = JsonConverter.convertArrayToType(jsonObject, Recipe.class);

        // then
        assertEquals(2, result.size());
        assertEquals("recipe 1", result.get(0).getName());
        assertEquals("recipe 2", result.get(1).getName());
    }

    @Test
    public void convertToJson_recipeObject_returnsString() {
        // given
        Recipe recipe = new Recipe(1, "recipe", new ArrayList<>(), new ArrayList<>(), 0, "image");

        // when
        String result = JsonConverter.convertToJson(recipe);

        // then
        assertThat(result, containsString("\"id\":1"));
        assertThat(result, containsString("\"name\":\"recipe\""));
        assertThat(result, containsString("\"image\":\"image\""));
    }
}