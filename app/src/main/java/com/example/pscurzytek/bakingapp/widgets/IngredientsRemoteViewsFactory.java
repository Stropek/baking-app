package com.example.pscurzytek.bakingapp.widgets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;

import java.util.ArrayList;

public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private ArrayList<String> ingredients;

    IngredientsRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;

        Bundle extras = intent.getExtras();

        if (extras != null) {
            ingredients = extras.getStringArrayList(Constants.BundleKeys.IngredientsList);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        ingredients = null;
    }

    @Override
    public int getCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == ListView.INVALID_POSITION || ingredients == null || ingredients.size() <= position) {
            return null;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_item);
        views.setTextViewText(R.id.ingredient_item_textView, ingredients.get(position));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
