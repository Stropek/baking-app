package com.example.pscurzytek.bakingapp.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class IngredientTests {

    @Test
    public void constructor_setsAllFields() {
        // when
        Ingredient ingredient = new Ingredient(1, "measure", "ingredient");

        // then
        assertEquals(1, ingredient.getQuantity());
        assertEquals("measure", ingredient.getMeasure());
        assertEquals("ingredient", ingredient.getIngredient());
    }

    @Test
    public void setQuantity_setsQuantity() {
        // given
        Ingredient ingredient = new Ingredient(1, "measure", "ingredient");

        // when
        ingredient.setQuantity(10);

        // then
        assertEquals(10, ingredient.getQuantity());
    }

    @Test
    public void setMeasure_setsMeasure() {
        // given
        Ingredient ingredient = new Ingredient(1, "measure", "ingredient");

        // when
        ingredient.setMeasure("changed measure");

        // then
        assertEquals("changed measure", ingredient.getMeasure());
    }

    @Test
    public void setIngredient_setsIngredient() {
        // given
        Ingredient ingredient = new Ingredient(1, "measure", "ingredient");

        // when
        ingredient.setIngredient("changed ingredient");

        // then
        assertEquals("changed ingredient", ingredient.getIngredient());
    }
}
