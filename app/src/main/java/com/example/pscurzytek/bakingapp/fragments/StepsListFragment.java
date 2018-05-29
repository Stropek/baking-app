package com.example.pscurzytek.bakingapp.fragments;

import android.app.Activity;
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
import com.example.pscurzytek.bakingapp.adapters.StepRecyclerAdapter;
import com.example.pscurzytek.bakingapp.models.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListFragment extends Fragment {

    private Activity activity;
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

        stepRecyclerAdapter = new StepRecyclerAdapter(activity, steps);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps_list, container, false);
        ButterKnife.bind(this, view);

        ingredientsTextView.setText("List of recipe ingredients");

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(stepsRecyclerView.getContext()));
        stepsRecyclerView.setAdapter(stepRecyclerAdapter);

//        Step step = (Step) recipesGridView.getItemAtPosition(position);
//
//        if (stepSelectedListener.isBigScreen()) {
//            stepSelectedListener.onStepSelected(step);
//        } else {
//            Intent intent = new Intent(view.getContext(), StepDetailsActivity.class);
//            intent.putExtra(Constants.BundleKeys.StepDetails, step);
//
//            startActivity(intent);
//        }

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

    private void loadStepsList(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            if (arguments != null) {
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
