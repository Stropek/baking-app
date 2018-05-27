package com.example.pscurzytek.bakingapp.models;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class IngredientTests {

    @Test
    public void createFromParcel_returnsParceledIngredient() {
        // given
        Ingredient ingredient = new Ingredient(1, "measure", "ingredient");

        // when
        Parcel parcel = Parcel.obtain();
        ingredient.writeToParcel(parcel, ingredient.describeContents());
        parcel.setDataPosition(0);
        Ingredient fromParcel = Ingredient.CREATOR.createFromParcel(parcel);

        // then
        assertEquals(fromParcel.getQuantity(), ingredient.getQuantity());
        assertEquals(fromParcel.getMeasure(), ingredient.getMeasure());
        assertEquals(fromParcel.getIngredient(), ingredient.getIngredient());
    }
}
