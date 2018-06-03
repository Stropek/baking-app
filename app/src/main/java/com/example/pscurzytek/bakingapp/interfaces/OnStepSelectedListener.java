package com.example.pscurzytek.bakingapp.interfaces;

import com.example.pscurzytek.bakingapp.models.Step;

public interface OnStepSelectedListener {

    void onStepSelected(Step step);

    boolean isBigScreen();
}
