package com.example.pscurzytek.bakingapp.utils;

import com.example.pscurzytek.bakingapp.models.Ingredient;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.models.Step;

import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    public static List<Recipe> createRecipes(int count) {
        List<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            recipes.add(createRecipe(i));
        }

        return recipes;
    }

    private static Recipe createRecipe(int id) {
        String name = String.format("recipe %s", id);
        int servings = 10 * id;
        ArrayList<Step> steps = createSteps(id);
        String image = String.format("image %s", id);

        return new Recipe(id, name, new ArrayList<>(), steps, servings, image);
    }

    public static ArrayList<Step> createSteps(int count) {
        ArrayList<Step> steps = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            steps.add(createStep(i));
        }

        return steps;
    }

    private static Step createStep(int stepNumber) {
        String shortDesc = String.format("short desc %s", stepNumber);
        String desc = String.format("desc %s", stepNumber);

        return new Step(stepNumber, shortDesc, desc, "video", "thumbnail");
    }

    public static ArrayList<Ingredient> createIngredients(int count) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            ingredients.add(createIngredient(i));
        }

        return ingredients;
    }

    private static Ingredient createIngredient(int ingredientNumber) {
        double quantity = (double) ingredientNumber;
        String measure = String.format("measure %s", ingredientNumber);
        String ingredient = String.format("ingredient %s", ingredientNumber);

        return new Ingredient(quantity, measure, ingredient);
    }
}
