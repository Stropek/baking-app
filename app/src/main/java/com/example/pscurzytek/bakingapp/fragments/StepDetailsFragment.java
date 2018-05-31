package com.example.pscurzytek.bakingapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.models.Step;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

    private Activity activity;

    @BindView(R.id.media_playerView) PlayerView mediaPlayerView;
    @BindView(R.id.step_instructions_textView) TextView instructionsTextView;
    @BindView(R.id.previous_button) Button previousButton;
    @BindView(R.id.next_button) Button nextButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, view);

        instructionsTextView.setText("Step instructions");

        return view;
    }
}
