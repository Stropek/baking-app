package com.example.pscurzytek.bakingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.activities.StepDetailsActivity;
import com.example.pscurzytek.bakingapp.adapters.StepRecyclerAdapter;
import com.example.pscurzytek.bakingapp.models.Ingredient;
import com.example.pscurzytek.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class StepsListFragment extends Fragment
    implements StepRecyclerAdapter.OnStepSelectedListener {

    private final int STEP_DETAILS_REQUEST = 1;

    private Activity activity;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;

    private StepRecyclerAdapter stepRecyclerAdapter;
    private StepsListFragment.OnStepSelectedListener stepSelectedListener;

    @BindView(R.id.ingredients_textView) TextView ingredientsTextView;
    @BindView(R.id.steps_recyclerView) RecyclerView stepsRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        loadStepsList(savedInstanceState);

        stepRecyclerAdapter = new StepRecyclerAdapter(activity, steps, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepSelectedListener = (OnStepSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_list_fragment, container, false);
        ButterKnife.bind(this, view);

        StringBuilder ingredientsText = new StringBuilder("Ingredients:");
        for (Ingredient ingredient: ingredients) {
            ingredientsText.append(String.format("\n- %s", ingredient));
        }
        ingredientsTextView.setText(ingredientsText.toString());

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(stepsRecyclerView.getContext()));
        stepsRecyclerView.setAdapter(stepRecyclerAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadStepsList(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

//        outState.putParcelable(Constants.StateKeys.Step, currentStep);
    }

    @Override
    public void loadDetails(Step step) {

        if (stepSelectedListener.isBigScreen()) {
            stepSelectedListener.onStepSelected(step);
        } else {
            Intent intent = new Intent(getContext(), StepDetailsActivity.class);
            intent.putExtra(Constants.BundleKeys.StepDetails, step);
            intent.putParcelableArrayListExtra(Constants.BundleKeys.StepsList, steps);

            startActivityForResult(intent, STEP_DETAILS_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case STEP_DETAILS_REQUEST:
                    data.getExtras();
                    break;
            }
        }
    }

    private void loadStepsList(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            if (arguments != null) {
                ingredients = arguments.getParcelableArrayList(Constants.BundleKeys.IngredientsList);
                steps = arguments.getParcelableArrayList(Constants.BundleKeys.StepsList);
//                currentStep = arguments.getParcelable(Constants.BundleKeys.StepDetails);
            }
        } else {
//            currentStep = savedInstanceState.getParcelable(Constants.StateKeys.Step);
        }
    }

    public interface OnStepSelectedListener {

        void onStepSelected(Step step);

        boolean isBigScreen();
    }
}
