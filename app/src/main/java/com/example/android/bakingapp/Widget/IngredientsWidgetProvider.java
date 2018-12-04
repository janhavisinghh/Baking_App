package com.example.android.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Activity.DetailActivity;
import com.example.android.bakingapp.Activity.MainActivity;
import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.Activity.DetailActivity.SHARED_PREFS;
import static com.example.android.bakingapp.Adapter.RecipeAdapter.MY_PREFS;


public class IngredientsWidgetProvider extends AppWidgetProvider {


    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, List<Ingredients> ingredientsList) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        views.setTextViewText(R.id.recipe_name_tv, recipeName);

        for(Ingredients ingredients : ingredientsList){
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(), R.layout.list_widget);
            ingredientView.setTextViewText(R.id.detail_quantity_tv,
                    String.valueOf(ingredients.getQuantity() + " "
                            +String.valueOf(ingredients.getMeasure())));
            ingredientView.setTextViewText(R.id.detail_ingredient_tv, String.valueOf(ingredients.getIngredient()));
            views.addView(R.id.ingredients_container, ingredientView);
        }
        appWidgetManager.updateAppWidget(appWidgetId,views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(sharedPreferences.getString("result",null), Recipe.class);
        String recipeName = recipe.getName();
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        ingredients = recipe.getIngredients();
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, ingredients);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}