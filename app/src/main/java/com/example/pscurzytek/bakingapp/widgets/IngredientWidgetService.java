package com.example.pscurzytek.bakingapp.widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.pscurzytek.bakingapp.models.Recipe;

import java.util.ArrayList;

public class IngredientWidgetService extends IntentService {

    public static String ACTION_UPDATE_RECIPES_WIDGETS = "com.example.pscurzytek.bakingapp.update_recipes_widgets";

    public IngredientWidgetService() {
        super(IngredientWidgetService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPES_WIDGETS.equals(action)) {
                handleActionUpdateRecipesWidgets();
            }
        }
    }

    public static void startActionUpdateRecipesWidgets(Context context) {
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPES_WIDGETS);
        context.startService(intent);
    }

    private void handleActionUpdateRecipesWidgets() {
        WidgetDataProvider dataProvider = new WidgetDataProvider(getApplication());
        ArrayList<Recipe> recipes = dataProvider.getRecipes();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));

        // update all widgets
        IngredientsWidgetProvider.updateAppWidgets(this, appWidgetManager, appWidgetIds, recipes);
    }
}
