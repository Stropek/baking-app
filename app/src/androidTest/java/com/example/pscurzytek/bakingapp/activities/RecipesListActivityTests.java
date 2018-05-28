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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.pscurzytek.bakingapp.utils.RecipeMatchers.withGridSize;
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
        String image = String.format("image %s", id);

        return new Recipe(id, name, new ArrayList<>(), new ArrayList<>(), servings, image);
    }
}
