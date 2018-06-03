package com.example.pscurzytek.bakingapp.activities;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.models.Ingredient;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.models.Step;
import com.example.pscurzytek.bakingapp.utils.ObjectFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTests {

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
        onView(withId(R.id.ingredients_textView)).check(matches(withText(containsString("- ingredient 4 - 4.0 measure 4"))));
        onView(withId(R.id.steps_recyclerView)).check(matches(hasDescendant(withText("5."))));
    }
}