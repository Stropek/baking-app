package com.example.pscurzytek.bakingapp.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.adapters.RecipeAdapter;
import com.example.pscurzytek.bakingapp.loaders.RecipeLoader;
import com.example.pscurzytek.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListActivity extends AppCompatActivity
   implements LoaderManager.LoaderCallbacks<Recipe> {

    @BindView(R.id.recipes_grid_view)
    public GridView recipesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        ButterKnife.bind(this);

        recipesGridView.setAdapter(new RecipeAdapter(this));
    }

    @Override
    public Loader<Recipe> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Recipe> loader, Recipe data) {

    }

    @Override
    public void onLoaderReset(Loader<Recipe> loader) {

    }
}
