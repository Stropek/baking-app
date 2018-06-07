package com.example.pscurzytek.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.models.Ingredient;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.models.Step;
import com.example.pscurzytek.bakingapp.utils.JsonConverter;
import com.example.pscurzytek.bakingapp.utils.ObjectFactory;
import com.example.pscurzytek.bakingapp.widgets.WidgetDataProvider;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTests {

    private final Context context = InstrumentationRegistry.getTargetContext();

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> testRule =
            new ActivityTestRule<>(RecipeDetailsActivity.class, false, false);

    @Test
    public void default_displaysRecipeDetails() {
        // given
        ArrayList<Ingredient> ingredients = ObjectFactory.createIngredients(5);
        ArrayList<Step> steps = ObjectFactory.createSteps(5);
        Recipe recipe = new Recipe(1, "recipe", ingredients, steps, 1, "image");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.RecipeDetails, recipe);

        // when
        testRule.launchActivity(intent);

        // then
        onView(withId(R.id.ingredients_textView)).check(matches(withText(containsString("- ingredient 2 - 2.0 measure 2"))));
        onView(withId(R.id.steps_recyclerView)).check(matches(hasDescendant(withText("2."))));
    }

    @Test
    public void toggleOrientation_displaysRecipeDetails() {
        // given
        ArrayList<Ingredient> ingredients = ObjectFactory.createIngredients(5);
        ArrayList<Step> steps = ObjectFactory.createSteps(5);
        Recipe recipe = new Recipe(1, "recipe", ingredients, steps, 1, "image");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.RecipeDetails, recipe);

        testRule.launchActivity(intent);

        // when
        testRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // then
        onView(withId(R.id.ingredients_textView)).check(matches(withText(containsString("- ingredient 2 - 2.0 measure 2"))));
        onView(withId(R.id.steps_recyclerView)).check(matches(hasDescendant(withText("2."))));
    }

    @Test
    public void toggleOrientationTwice_displaysRecipeDetails() {
        // given
        ArrayList<Ingredient> ingredients = ObjectFactory.createIngredients(5);
        ArrayList<Step> steps = ObjectFactory.createSteps(5);
        Recipe recipe = new Recipe(1, "recipe", ingredients, steps, 1, "image");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.RecipeDetails, recipe);

        testRule.launchActivity(intent);

        // when
        testRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        testRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // then
        onView(withId(R.id.ingredients_textView)).check(matches(withText(containsString("- ingredient 2 - 2.0 measure 2"))));
        onView(withId(R.id.steps_recyclerView)).check(matches(hasDescendant(withText("2."))));
    }

    @Test
    public void openStepDetailsAndPressHomeButton_displaysRecipeDetails() {
        // given
        ArrayList<Ingredient> ingredients = ObjectFactory.createIngredients(5);
        ArrayList<Step> steps = ObjectFactory.createSteps(5);
        Recipe recipe = new Recipe(1, "recipe", ingredients, steps, 1, "image");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.RecipeDetails, recipe);

        testRule.launchActivity(intent);

        // when
        onView(withId(R.id.steps_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        // then
        onView(withId(R.id.ingredients_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.steps_recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void toggleWidgetRecipe_newRecipe_addsRecipeToSharedPreferences() {
        // given
        ArrayList<Ingredient> ingredients = ObjectFactory.createIngredients(5);
        ArrayList<Step> steps = ObjectFactory.createSteps(5);
        Recipe recipe = new Recipe(1, "recipe", ingredients, steps, 1, "image");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.RecipeDetails, recipe);

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SharedPreferences.Name, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        testRule.launchActivity(intent);

        // when
        onView(withId(R.id.toggle_widget_recipe)).perform(click());
        Set<String> result = sharedPreferences.getStringSet(Constants.SharedPreferences.Recipes, new HashSet<>());

        // then
        assertEquals(1, result.size());
        assertThat(result.toArray()[0].toString(), containsString("\"id\":1"));
    }

    @Test
    public void toggleWidgetRecipe_existingRecipe_removesRecipeToSharedPreferences() {
        // given

        ArrayList<Ingredient> ingredients = ObjectFactory.createIngredients(5);
        ArrayList<Step> steps = ObjectFactory.createSteps(5);
        Recipe recipe = new Recipe(1, "recipe", ingredients, steps, 1, "image");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.RecipeDetails, recipe);

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SharedPreferences.Name, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        Set<String> recipes = new HashSet<>();
        recipes.add(JsonConverter.convertToJson(recipe));
        sharedPreferences.edit().putStringSet(Constants.SharedPreferences.Recipes, recipes).commit();

        testRule.launchActivity(intent);

        // when
        onView(withId(R.id.toggle_widget_recipe)).perform(click());
        Set<String> result = sharedPreferences.getStringSet(Constants.SharedPreferences.Recipes, new HashSet<>());

        // then
        assertEquals(0, result.size());
    }
}
