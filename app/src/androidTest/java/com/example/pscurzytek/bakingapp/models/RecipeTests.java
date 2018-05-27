package com.example.pscurzytek.bakingapp.models;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RecipeTests {

    @Test
    public void createFromParcel_returnsParceledRecipe() {
        // given
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "cup", "sugar"));

        List<Step> steps = new ArrayList<>();
        steps.add(new Step(1, "short desc", "desc", "video", "thumbnail"));

        Recipe recipe = new Recipe(1, "recipe", ingredients, steps, 1, "");

        // when
        Parcel parcel = Parcel.obtain();
        recipe.writeToParcel(parcel, recipe.describeContents());
        parcel.setDataPosition(0);
        Recipe fromParcel = Recipe.CREATOR.createFromParcel(parcel);

        // then
        assertEquals(fromParcel.getId(), recipe.getId());
        assertEquals(fromParcel.getName(), recipe.getName());
        assertEquals(fromParcel.getIngredients().get(0).getIngredient(), recipe.getIngredients().get(0).getIngredient());
        assertEquals(fromParcel.getSteps().get(0).getDescription(), recipe.getSteps().get(0).getDescription());
        assertEquals(fromParcel.getServings(), recipe.getServings());
        assertEquals(fromParcel.getImage(), recipe.getImage());
    }

    @Test
    public void newArray_returnsNewArrayOfGivenSize() {
        // when
        Recipe[] recipes = Recipe.CREATOR.newArray(10);

        // then
        assertEquals(10, recipes.length);
    }
}
