package com.diegomalone.bakingapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Recipe;

/**
 * Created by Diego Malone on 21/01/18.
 */

public class RecipeCardView extends LinearLayout {

    private Context context;
    private Recipe recipe;

    private TextView recipeNameTextView;
    private ImageView recipeImage;

    public RecipeCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RecipeCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecipeCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_recipe, this, true);

        recipeNameTextView = view.findViewById(R.id.recipeNameTextView);
        recipeImage = view.findViewById(R.id.recipeImage);
    }

    public void setRecipe(Recipe recipe) {
        if (recipe == null) return;

        this.recipe = recipe;

        recipeNameTextView.setText(recipe.getName());

        if (recipe.getImageURL() != null && !recipe.getImageURL().isEmpty()) {
            recipeImage.setVisibility(VISIBLE);

            Glide.with(context)
                    .load(recipe.getImageURL())
                    .into(recipeImage);
        } else {
            recipeImage.setVisibility(GONE);
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
