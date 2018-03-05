package com.diegomalone.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Recipe;
import com.google.gson.Gson;

/**
 * Created by malone on 25/02/18.
 */

public class RecipeWidgetManager {

    private static final String PREFS_FILE_NAME = "baking_app";
    private static final String KEY_WIDGET_RECIPE_ID = "recipeId";

    private Context context;
    private SharedPreferences sharedPreferences;

    public RecipeWidgetManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setRecipe(Recipe recipe) {
        String recipeJson = new Gson().toJson(recipe);

        sharedPreferences.edit().putString(KEY_WIDGET_RECIPE_ID, recipeJson).apply();

        updateWidget();
    }

    public Recipe getRecipe() {
        String recipeJson = sharedPreferences.getString(KEY_WIDGET_RECIPE_ID, null);
        if (recipeJson == null) return null;

        return new Gson().fromJson(recipeJson, Recipe.class);
    }

    private void updateWidget() {
        Intent intent = new Intent(context, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, RecipeWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.sendBroadcast(intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredient_list);
    }
}
