package com.diegomalone.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.ui.activity.RecipeListActivity;
import com.diegomalone.bakingapp.ui.activity.RecipeStepListActivity;

import static com.diegomalone.bakingapp.utils.FlowController.RECIPE_EXTRA;

/**
 * Created by malone on 13/02/18.
 */

public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient);


        PendingIntent pendingIntent;

        if (recipe != null) {
            views.setTextViewText(R.id.widget_title, recipe.getName());
            views.setRemoteAdapter(R.id.widget_ingredient_list, new Intent(context, RecipeIngredientListRemoteViewService.class));

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(RecipeListActivity.class);
            stackBuilder.addNextIntent(new Intent(context, RecipeListActivity.class));

            Intent intent = new Intent (context, RecipeStepListActivity.class);
            intent.putExtra(RECIPE_EXTRA, recipe);
            stackBuilder.addNextIntent(intent);

            pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            views.setTextViewText(R.id.widget_title, context.getString(R.string.widget_no_recipe_selected));
            Intent intent = new Intent(context, RecipeListActivity.class);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        }

        views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeWidgetManager recipeWidgetManager = new RecipeWidgetManager(context);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeWidgetManager.getRecipe());
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
