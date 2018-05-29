package com.example.pscurzytek.bakingapp.activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.pscurzytek.bakingapp.BakingApp;
import com.example.pscurzytek.bakingapp.DaggerTestAppComponent;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.TestAppComponent;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.models.Step;
import com.example.pscurzytek.bakingapp.modules.TestRecipeServiceModule;
import com.example.pscurzytek.bakingapp.services.RecipeService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
        List<Recipe> recipes = createRecipes(10);
        when(recipeService.getRecipes()).thenReturn(recipes);

        // when
        testRule.launchActivity(null);

        // then
        onView(withId(R.id.recipes_grid_view)).check(matches(withGridSize(10)));
    }

    @Test
    public void clickRecipe_opensRecipeDetailsActivity() {
        // given
        List<Recipe> recipes = createRecipes(5);
        when(recipeService.getRecipes()).thenReturn(recipes);
        testRule.launchActivity(null);

        // when
        onData(withRecipeId(3)).perform(click());

        // then
        onView(withId(R.id.steps_list_fragment)).check(matches(isDisplayed()));
    }

    private List<Recipe> createRecipes(int count) {
        List<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            recipes.add(createRecipe(i));
        }

        return recipes;
    }

    private Recipe createRecipe(int id) {
        String name = String.format("recipe %s", id);
        int servings = 10 * id;
        ArrayList<Step> steps = createSteps(id);
        String image = String.format("image %s", id);

        return new Recipe(id, name, new ArrayList<>(), steps, servings, image);
    }

    private ArrayList<Step> createSteps(int count) {
        ArrayList<Step> steps = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            steps.add(createStep(i));
        }

        return steps;
    }

    private Step createStep(int stepNumber) {
        String shortDesc = String.format("short desc %s", stepNumber);
        String desc = String.format("desc %s", stepNumber);

        return new Step(stepNumber, shortDesc, desc, "video", "thumbnail");
    }
}
