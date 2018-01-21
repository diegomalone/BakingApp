package com.diegomalone.bakingapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.ui.events.RecipeClickListener;
import com.diegomalone.bakingapp.view.RecipeCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Malone on 21/01/18.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    private RecipeClickListener recipeClickListener;

    public RecipeListAdapter(Context context, List<Recipe> recipeList, RecipeClickListener recipeClickListener) {
        this.context = context;
        this.recipeClickListener = recipeClickListener;
        updateList(recipeList);
    }

    public void updateList(List<Recipe> recipeList) {
        this.recipeList.clear();

        if (recipeList != null) {
            this.recipeList.addAll(recipeList);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecipeCardView recipeCardView = new RecipeCardView(context);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recipeCardView.setLayoutParams(layoutParams);

        return new ViewHolder(recipeCardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.recipeCardView.setRecipe(recipe);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        protected RecipeCardView recipeCardView;

        protected ViewHolder(View itemView) {
            super(itemView);

            recipeCardView = (RecipeCardView) itemView;

            recipeCardView.setOnClickListener(view ->
                    recipeClickListener.onRecipeClick(recipeCardView.getRecipe()));
        }
    }
}
