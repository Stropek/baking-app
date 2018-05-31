package com.example.pscurzytek.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.fragments.StepDetailsFragment;
import com.example.pscurzytek.bakingapp.fragments.StepsListFragment;
import com.example.pscurzytek.bakingapp.models.Step;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity
    implements StepsListFragment.OnStepSelectedListener {

    private StepsListFragment stepsListFragment;
    private StepDetailsFragment stepDetailsFragment;

    private boolean isBigScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isBigScreen = findViewById(R.id.step_details) != null;

        if (savedInstanceState == null) {
            loadStepsListFragment();
            if (isBigScreen) {
                loadStepDetailsFragment();
            }
        } else {
            // TODO: load persisted state
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // TODO: persist state
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
        bundle.putParcelableArrayList(Constants.BundleKeys.StepsList, getIntent().getParcelableArrayListExtra(Constants.BundleKeys.StepsList));
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
}
