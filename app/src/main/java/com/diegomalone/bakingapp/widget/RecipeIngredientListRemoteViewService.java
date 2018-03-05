package com.diegomalone.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Ingredient;
import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.utils.QuantityUtils;

import java.util.List;

/**
 * Created by malone on 05/03/18.
 */

public class RecipeIngredientListRemoteViewService extends RemoteViewsService {

    private RecipeWidgetManager recipeWidgetManager;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {

            private Recipe recipe;

            @Override
            public void onCreate() {
                recipeWidgetManager = new RecipeWidgetManager(RecipeIngredientListRemoteViewService.this);
                recipe = recipeWidgetManager.getRecipe();
            }

            @Override
            public void onDataSetChanged() {
                recipe = recipeWidgetManager.getRecipe();
            }

            @Override
            public void onDestroy() {
            }

            @Override
            public int getCount() {
                if (recipe != null && recipe.getIngredientList() != null) {
                    return recipe.getIngredientList().size();
                }

                return 0;
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (recipe == null) return null;

                List<Ingredient> ingredientList = recipe.getIngredientList();

                if (ingredientList == null) return null;

                Ingredient ingredient = ingredientList.get(position);

                if (ingredient == null) return null;

                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_ingredient_item);

                remoteViews.setTextViewText(R.id.ingredient_name, ingredient.getName());
                remoteViews.setTextViewText(R.id.ingredient_quantity, String.valueOf(QuantityUtils.getIngredientQuantity(ingredient.getQuantity())));
                remoteViews.setTextViewText(R.id.ingredient_measure, String.valueOf(ingredient.getMeasure()));

                return remoteViews;
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
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
    }
}
