package com.diegomalone.bakingapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Ingredient;
import com.diegomalone.bakingapp.model.Step;
import com.diegomalone.bakingapp.ui.events.StepClickListener;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by malone on 12/02/18.
 */

public class RecipeContentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int INGREDIENT_VIEW_TYPE = 0;
    public static final int STEP_VIEW_TYPE = 1;

    private ArrayList<Ingredient> ingredientList = new ArrayList<>();
    private ArrayList<Step> stepList = new ArrayList<>();

    private StepClickListener stepClickListener;

    public RecipeContentListAdapter(List<Ingredient> ingredientList,
                                    List<Step> stepList, StepClickListener stepClickListener) {
        this.stepClickListener = stepClickListener;

        this.ingredientList.addAll(ingredientList);
        this.stepList.addAll(stepList);

        Timber.i("Size %s %s", ingredientList.size(), stepList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == INGREDIENT_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
            return new IngredientViewHolder(view);
        } else if (viewType == STEP_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step_description, parent, false);
            return new StepViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < ingredientList.size()) {
            return INGREDIENT_VIEW_TYPE;
        }

        return STEP_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == INGREDIENT_VIEW_TYPE) {
            ((IngredientViewHolder) holder).setDataPosition(position);
        } else if (getItemViewType(position) == STEP_VIEW_TYPE) {
            ((StepViewHolder) holder).setDataPosition(position - ingredientList.size());
        }
    }

    @Override
    public int getItemCount() {
        return ingredientList.size() + stepList.size();
    }

    protected class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private Ingredient ingredient;

        protected IngredientViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.ingredientTextView);
        }

        protected void setDataPosition(int position) {
            ingredient = ingredientList.get(position);
            textView.setText(String.valueOf(ingredient.getQuantity()));
        }
    }

    protected class StepViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private Step step;

        protected StepViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.stepDescriptionTextView);

            itemView.setOnClickListener(view ->
                    stepClickListener.onStepClick(step)
            );
        }

        protected void setDataPosition(int position) {
            step = stepList.get(position);
            textView.setText(step.getDescription());
        }
    }
}
