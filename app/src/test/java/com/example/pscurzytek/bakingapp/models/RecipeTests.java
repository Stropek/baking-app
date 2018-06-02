package com.example.pscurzytek.bakingapp.models;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class RecipeTests {

    @Test
    public void constructor_setsAllFields() {
        // given
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "cup", "sugar"));

        ArrayList<Step> steps = new ArrayList<>();
        steps.add(new Step(1, "short desc", "desc", "video", "thumbnail"));

        // when
        Recipe recipe = new Recipe(1, "recipe", ingredients, steps, 1, "image");

        // then
        assertEquals(1, recipe.getId());
        assertEquals("recipe", recipe.getName());
        assertEquals(1, recipe.getIngredients().size());
        assertEquals(1, recipe.getSteps().size());
        assertEquals(1, recipe.getServings());
        assertEquals("image", recipe.getImage());
    }

    @Test
    public void setId_setsId() {
        // given
        Recipe recipe = new Recipe(1, "recipe", new ArrayList<>(), new ArrayList<>(), 1, "image");

        // when
        recipe.setId(10);

        // then
        assertEquals(10, recipe.getId());
    }

    @Test
    public void setName_setsName() {
        // given
        Recipe recipe = new Recipe(1, "recipe", new ArrayList<>(), new ArrayList<>(), 1, "image");

        // when
        recipe.setName("changed name");

        // then
        assertEquals("changed name", recipe.getName());
    }

    @Test
    public void setIngredients_setsIngredients() {
        // given
        Recipe recipe = new Recipe(1, "recipe", new ArrayList<>(), new ArrayList<>(), 1, "image");

        // when
        ArrayList<Ingredient> changedIngredients = new ArrayList<>();
        changedIngredients.add(new Ingredient(1, "cup", "sugar"));

        recipe.setIngredients(changedIngredients);

        // then
        assertEquals(1, recipe.getIngredients().size());
    }

    @Test
    public void setSteps_setsSteps() {
        // given
        Recipe recipe = new Recipe(1, "recipe", new ArrayList<>(), new ArrayList<>(), 1, "image");

        // when
        ArrayList<Step> changedSteps = new ArrayList<>();
        changedSteps.add(new Step(1, "short desc", "desc", "video", "thumbnail"));

        recipe.setSteps(changedSteps);

        // then
        assertEquals(1, recipe.getSteps().size());
    }

    @Test
    public void setServings_setsServings() {
        // given
        Recipe recipe = new Recipe(1, "recipe", new ArrayList<>(), new ArrayList<>(), 1, "image");

        // when
        recipe.setServings(10);

        // then
        assertEquals(10, recipe.getServings());
    }

    @Test
    public void setImage_setsImage() {
        // given
        Recipe recipe = new Recipe(1, "recipe", new ArrayList<>(), new ArrayList<>(), 1, "image");

        // when
        recipe.setImage("changed image");

        // then
        assertEquals("changed image", recipe.getImage());
    }
}
