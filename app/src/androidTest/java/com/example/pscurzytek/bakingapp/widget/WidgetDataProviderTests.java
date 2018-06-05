package com.example.pscurzytek.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.utils.ObjectFactory;
import com.example.pscurzytek.bakingapp.widgets.WidgetDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WidgetDataProviderTests {

    private final Context context = InstrumentationRegistry.getTargetContext();

    @Test
    public void getRecipes_noEntryInSharedPreferences_returnsEmptyArrayList() {
        // given
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SharedPreferences.Name, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        WidgetDataProvider provider = new WidgetDataProvider(sharedPreferences);

        // when
        ArrayList<Recipe> result = provider.getRecipes();

        // then
        assertEquals(0, result.size());
    }

    @Test
    public void getRecipes_sharedPreferencesContainRecipes_returnsArrayListOfRecipes() {
        // given
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SharedPreferences.Name, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        Set<String> recipes = new HashSet<>();
        recipes.add(ObjectFactory.createRecipeString(1));
        recipes.add(ObjectFactory.createRecipeString(2));

        sharedPreferences.edit().putStringSet(Constants.SharedPreferences.Recipes, recipes).commit();

        WidgetDataProvider provider = new WidgetDataProvider(sharedPreferences);

        // when
        ArrayList<Recipe> result = provider.getRecipes();

        // then
        assertEquals(2, result.size());
    }
}
