package com.example.pscurzytek.bakingapp.utils;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.pscurzytek.bakingapp.models.Recipe;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RecipeMatchers {

    public static Matcher<View> withGridSize (final int size) {
        return new TypeSafeMatcher<View> () {
            @Override public boolean matchesSafely (final View view) {
                return ((GridView) view).getCount () == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("GridView should have " + size + " items");
            }
        };
    }
}
