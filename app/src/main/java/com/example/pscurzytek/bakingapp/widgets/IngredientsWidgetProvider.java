package com.example.pscurzytek.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.activities.RecipeDetailsActivity;
import com.example.pscurzytek.bakingapp.activities.RecipesListActivity;
import com.example.pscurzytek.bakingapp.models.Ingredient;
import com.example.pscurzytek.bakingapp.models.Recipe;

import java.util.ArrayList;

public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, ArrayList<Recipe> recipes) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        Intent intent;

        if (recipes != null && recipes.size() > 0) {
            views.setViewVisibility(R.id.recipe_name_textView, View.VISIBLE);
            views.setViewVisibility(R.id.ingredients_listView, View.VISIBLE);
            views.setViewVisibility(R.id.empty_ingredients_textView, View.GONE);

            Recipe recipe = recipes.get(0);

            views.setTextViewText(R.id.recipe_name_textView, recipe.getName());

            Intent remoteAdapterIntent = new Intent(context, IngredientsRemoteViewsService.class);

            ArrayList<String> ingredients = new ArrayList<>();
            for (Ingredient ingredient: recipe.getIngredients()) {
                ingredients.add(ingredient.toString());
            }
            remoteAdapterIntent.putStringArrayListExtra(Constants.BundleKeys.IngredientsList, ingredients);
            views.setRemoteAdapter(R.id.ingredients_listView, remoteAdapterIntent);

            intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra(Constants.BundleKeys.RecipeDetails, recipe);
        } else {
            views.setViewVisibility(R.id.recipe_name_textView, View.GONE);
            views.setViewVisibility(R.id.ingredients_listView, View.GONE);
            views.setViewVisibility(R.id.empty_ingredients_textView, View.VISIBLE);

            intent = new Intent(context, RecipesListActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.baking_app_button, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, ArrayList<Recipe> recipes) {
        for(int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipes);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        IngredientsIntentService.startActionUpdateRecipesWidgets(context);
    }
}
