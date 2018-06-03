package com.example.pscurzytek.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.fragments.StepDetailsFragment;
import com.example.pscurzytek.bakingapp.fragments.StepsListFragment;
import com.example.pscurzytek.bakingapp.models.Recipe;
import com.example.pscurzytek.bakingapp.models.Step;
import com.example.pscurzytek.bakingapp.widgets.WidgetDataProvider;

public class RecipeDetailsActivity extends AppCompatActivity
    implements StepsListFragment.OnStepSelectedListener {

    private Recipe recipe;

    private StepsListFragment stepsListFragment;
    private StepDetailsFragment stepDetailsFragment;

    // TODO: inject with Dagger for testing
    private WidgetDataProvider widgetDataProvider;

    private boolean isBigScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity);

        widgetDataProvider = new WidgetDataProvider(getApplication());
        isBigScreen = findViewById(R.id.step_details) != null;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras != null) {
                recipe = extras.getParcelable(Constants.BundleKeys.RecipeDetails);
                loadStepsListFragment();
                if (isBigScreen) {
                    loadStepDetailsFragment();
                }
            }
        } else {
            // TODO: load persisted state
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            boolean isEnabled = extras != null && extras.getString(Constants.BundleKeys.CallingActivity) != null;
            supportActionBar.setDisplayHomeAsUpEnabled(isEnabled);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // TODO: persist state
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.recipe_details_menu, menu);

        CheckBox checkBox = (CheckBox) menu.findItem(R.id.toggle_widget_recipe).getActionView();
        checkBox.setChecked(widgetDataProvider.isPersisted(recipe));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            widgetDataProvider.toggleRecipe(this, recipe);
        });

        invalidateOptionsMenu();
        return true;
    }

    @Override
    public void onStepSelected(Step step) {
        Log.d("TAG", "load step data to recipe details");

        if (isBigScreen) {
            Log.d("TAG", "load step data to recipe details fragment");
        }
    }

    @Override
    public boolean isBigScreen() {
        return isBigScreen;
    }

    private void loadStepsListFragment() {
        stepsListFragment = getStepsListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.steps_list, stepsListFragment);
        fragmentTransaction.commit();

        invalidateOptionsMenu();
    }

    private void loadStepDetailsFragment() {
        stepDetailsFragment = getStepDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.step_details, stepDetailsFragment);
        fragmentTransaction.commit();

        invalidateOptionsMenu();
    }

    public StepsListFragment getStepsListFragment() {
        StepsListFragment fragment = new StepsListFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.BundleKeys.IngredientsList, recipe.getIngredients());
        bundle.putParcelableArrayList(Constants.BundleKeys.StepsList, recipe.getSteps());
        fragment.setArguments(bundle);

        return fragment;
    }

    public StepDetailsFragment getStepDetailsFragment() {
        StepDetailsFragment fragment = new StepDetailsFragment();

        Bundle bundle = new Bundle();
        // TODO: pass any arguments that fragment needs
        fragment.setArguments(bundle);

        return fragment;
    }
}
