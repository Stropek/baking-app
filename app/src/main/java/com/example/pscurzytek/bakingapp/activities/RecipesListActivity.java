package com.example.pscurzytek.bakingapp.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridView;

import com.example.pscurzytek.bakingapp.BakingApp;
import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.adapters.RecipeAdapter;
import com.example.pscurzytek.bakingapp.loaders.RecipeLoader;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.services.RecipeService;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListActivity extends AppCompatActivity
   implements LoaderManager.LoaderCallbacks<List<Recipe>> {

    private final int RECIPE_LOADER_ID = 1;

    private RecipeAdapter recipeAdapter;

    @Inject
    public RecipeService recipeService;

    @BindView(R.id.recipes_grid_view)
    public GridView recipesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        ButterKnife.bind(this);

        BakingApp app = (BakingApp) this.getApplication();
        app.appComponent.inject(this);

        recipeAdapter = new RecipeAdapter(this);
        recipesGridView.setAdapter(recipeAdapter);
        recipesGridView.setOnItemClickListener((parent, view, position, id) -> {

            Recipe recipe = (Recipe) recipesGridView.getItemAtPosition(position);

            Intent intent = new Intent(view.getContext(), RecipeDetailsActivity.class);
            intent.putParcelableArrayListExtra(Constants.BundleKeys.IngredientsList, recipe.getIngredients());
            intent.putParcelableArrayListExtra(Constants.BundleKeys.StepsList, recipe.getSteps());

            startActivity(intent);
        });

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (displayMetrics.widthPixels <= 1200) {
            recipesGridView.setNumColumns(1);
        }

        getLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(this, recipeService);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        recipeAdapter.clear();
        recipeAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        recipeAdapter.clear();
    }
}
