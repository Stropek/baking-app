package com.example.pscurzytek.bakingapp.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.GridView;
import android.widget.TextView;

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

    @BindView(R.id.recipes_grid_view) public GridView recipesGridView;
    @BindView(R.id.no_recipes_textView) public TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_list_activity);

        ButterKnife.bind(this);

        BakingApp app = (BakingApp) this.getApplication();
        app.appComponent.inject(this);

        recipeAdapter = new RecipeAdapter(this);
        recipesGridView.setAdapter(recipeAdapter);
        recipesGridView.setOnItemClickListener((parent, view, position, id) -> {

            Recipe recipe = (Recipe) recipesGridView.getItemAtPosition(position);

            Intent intent = new Intent(view.getContext(), RecipeDetailsActivity.class);
            intent.putExtra(Constants.BundleKeys.CallingActivity, RecipesListActivity.class.getName());
            intent.putExtra(Constants.BundleKeys.RecipeDetails, recipe);

            startActivity(intent);
        });
        recipesGridView.setEmptyView(emptyView);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (displayMetrics.widthPixels <= 1200) {
            recipesGridView.setNumColumns(1);
        }

        if (isConnected()) {
            getLoaderManager().initLoader(RECIPE_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(this, recipeService);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        recipeAdapter.clear();
        if (data != null) {
            recipeAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        recipeAdapter.clear();
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
