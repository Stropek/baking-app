package com.example.pscurzytek.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.fragments.StepDetailsFragment;
import com.example.pscurzytek.bakingapp.models.Step;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity {

    private ArrayList<Step> steps;
    private StepDetailsFragment stepDetailsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        steps = getIntent().getParcelableArrayListExtra(Constants.BundleKeys.StepsList);
        if (savedInstanceState == null) {
            loadStepDetailsFragment();
        } else {
            // TODO: load persisted state
        }
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

    private void loadStepDetailsFragment() {
        stepDetailsFragment = getStepDetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.step_details, stepDetailsFragment);
        fragmentTransaction.commit();

        invalidateOptionsMenu();
    }

    private StepDetailsFragment getStepDetailsFragment() {
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
    }
}
