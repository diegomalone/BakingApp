package com.diegomalone.bakingapp.utils;

import android.content.Context;
import android.content.Intent;

import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.ui.activity.RecipeStepListActivity;

/**
 * Created by Diego Malone on 21/01/18.
 */

public class FlowController {

    public static final String RECIPE_EXTRA = "recipe";

    public static void openRecipeStepScreen(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeStepListActivity.class);
        intent.putExtra(RECIPE_EXTRA, recipe);

        context.startActivity(intent);
    }
}
