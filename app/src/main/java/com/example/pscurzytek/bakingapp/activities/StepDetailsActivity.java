package com.example.pscurzytek.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.fragments.StepDetailsFragment;
import com.example.pscurzytek.bakingapp.interfaces.OnStepNavigationListener;
import com.example.pscurzytek.bakingapp.models.Step;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity
    implements OnStepNavigationListener {

    private ArrayList<Step> steps;
    private StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_details_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            steps = intent.getParcelableArrayListExtra(Constants.BundleKeys.StepsList);
            Step step = intent.getParcelableExtra(Constants.BundleKeys.StepDetails);
            loadStepDetailsFragment(step);
        } else {
            steps = savedInstanceState.getParcelableArrayList(Constants.BundleKeys.StepsList);
            stepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, Constants.BundleKeys.StepDetailsFragment);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(Constants.BundleKeys.StepsList, steps);
        getSupportFragmentManager().putFragment(outState, Constants.BundleKeys.StepDetailsFragment, stepDetailsFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent resultIntent = new Intent();
                resultIntent.putParcelableArrayListExtra(Constants.BundleKeys.StepsList, steps);
                setResult(RESULT_OK, resultIntent);
                finish();
                break;
        }

        return true;
    }

    @Override
    public void navigateToStep(int stepId) {
        if (stepId >= steps.size()) {
            stepId = 0;
        } else if (stepId < 0) {
            stepId = steps.size() - 1;
        }

        loadStepDetailsFragment(steps.get(stepId));
    }

    private void loadStepDetailsFragment(Step step) {
        stepDetailsFragment = getStepDetailsFragment(step);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.step_details, stepDetailsFragment);
        fragmentTransaction.commit();

        invalidateOptionsMenu();
    }

    private StepDetailsFragment getStepDetailsFragment(Step step) {
        StepDetailsFragment fragment = new StepDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKeys.StepDetails, step);
        fragment.setArguments(bundle);
        return fragment;
    }
}
