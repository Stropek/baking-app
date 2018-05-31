package com.example.pscurzytek.bakingapp.activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.models.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.pscurzytek.bakingapp.utils.RecipeMatchers.withGridSize;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTests {

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> testRule =
            new ActivityTestRule<>(RecipeDetailsActivity.class, false, false);

    @Test
    public void default_displaysRecipeDetails() {
        // given
//        List<Recipe> recipes = createRecipes(10);

        // when
        testRule.launchActivity(null);

        // then
        onView(withId(R.id.recipes_grid_view)).check(matches(withGridSize(10)));
    }
}
