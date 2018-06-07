package com.example.pscurzytek.bakingapp.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.pscurzytek.bakingapp.BakingApp;
import com.example.pscurzytek.bakingapp.DaggerTestAppComponent;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.TestAppComponent;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.modules.TestRecipeServiceModule;
import com.example.pscurzytek.bakingapp.services.RecipeService;
import com.example.pscurzytek.bakingapp.utils.ObjectFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.util.List;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.pscurzytek.bakingapp.utils.RecipeMatchers.withGridSize;
import static com.example.pscurzytek.bakingapp.utils.RecipeMatchers.withRecipeId;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class RecipesListActivityTests {

    private final Context context = InstrumentationRegistry.getTargetContext();

    @Rule
    public ActivityTestRule<RecipesListActivity> testRule =
            new ActivityTestRule<>(RecipesListActivity.class, false, false);

    @Inject
    RecipeService recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        BakingApp app = (BakingApp) context.getApplicationContext();

        TestAppComponent testAppComponent = DaggerTestAppComponent.builder()
                .recipeServiceModule(new TestRecipeServiceModule())
                .build();

        app.appComponent = testAppComponent;
        testAppComponent.inject(this);
    }

    @Test
    public void default_displaysRecipes() {
        // given
        List<Recipe> recipes = ObjectFactory.createRecipes(10);
        when(recipeService.getRecipes()).thenReturn(recipes);

        // when
        testRule.launchActivity(null);

        // then
        onView(withId(R.id.recipes_grid_view)).check(matches(withGridSize(10)));
    }

    @Test
    public void toggleOrientation_displaysRecipes() {
        // given
        List<Recipe> recipes = ObjectFactory.createRecipes(10);
        when(recipeService.getRecipes()).thenReturn(recipes);

        testRule.launchActivity(null);

        // when
        testRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // then
        onView(withId(R.id.recipes_grid_view)).check(matches(withGridSize(10)));
    }

    @Test
    public void clickRecipe_opensRecipeDetailsActivity() {
        // given
        List<Recipe> recipes = ObjectFactory.createRecipes(5);
        when(recipeService.getRecipes()).thenReturn(recipes);
        testRule.launchActivity(null);

        // when
        onData(withRecipeId(3)).perform(click());

        // then
        onView(withId(R.id.steps_list_fragment)).check(matches(isDisplayed()));
    }
}
