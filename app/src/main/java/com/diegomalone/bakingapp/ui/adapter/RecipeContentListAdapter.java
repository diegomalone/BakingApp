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
import com.diegomalone.bakingapp.utils.QuantityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by malone on 12/02/18.
 */

public class RecipeContentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int INGREDIENT_TITLE_VIEW_TYPE = 0;
    private static final int STEP_TITLE_VIEW_TYPE = 1;
    private static final int INGREDIENT_VIEW_TYPE = 2;
    private static final int STEP_VIEW_TYPE = 3;

    private ArrayList<Ingredient> ingredientList = new ArrayList<>();
    private ArrayList<Step> stepList = new ArrayList<>();

    private StepClickListener stepClickListener;

    private Context context;

    public RecipeContentListAdapter(Context context, List<Ingredient> ingredientList,
                                    List<Step> stepList, StepClickListener stepClickListener) {
        this.context = context;
        this.stepClickListener = stepClickListener;

        this.ingredientList.addAll(ingredientList);
        this.stepList.addAll(stepList);
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
        } else if (viewType == INGREDIENT_TITLE_VIEW_TYPE ||
                viewType == STEP_TITLE_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title, parent, false);
            return new TitleViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return INGREDIENT_TITLE_VIEW_TYPE;
        } else if (position > 0 && position <= ingredientList.size()) {
            return INGREDIENT_VIEW_TYPE;
        } else if (position == (ingredientList.size() + 1)) {
            return STEP_TITLE_VIEW_TYPE;
        }

        return STEP_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == INGREDIENT_VIEW_TYPE) {
            ((IngredientViewHolder) holder).setDataPosition(position - 1);
        } else if (getItemViewType(position) == STEP_VIEW_TYPE) {
            ((StepViewHolder) holder).setDataPosition(position - 2 - ingredientList.size());
        } else if (getItemViewType(position) == INGREDIENT_TITLE_VIEW_TYPE) {
            ((TitleViewHolder) holder).setTitle(context.getString(R.string.recipe_contents_ingredients_title));
        } else if (getItemViewType(position) == STEP_TITLE_VIEW_TYPE) {
            ((TitleViewHolder) holder).setTitle(context.getString(R.string.recipe_contents_steps_title));
        }
    }

    @Override
    public int getItemCount() {
        return ingredientList.size() + stepList.size() + 2;
    }

    protected class TitleViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;

        protected TitleViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
        }

        protected void setTitle(String title) {
            titleTextView.setText(title);
        }
    }

    protected class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView, quantityTextView, measureTextView;
        private Ingredient ingredient;

        protected IngredientViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.ingredientTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            measureTextView = itemView.findViewById(R.id.measureTextView);
        }

        protected void setDataPosition(int position) {
            ingredient = ingredientList.get(position);

            nameTextView.setText(String.valueOf(ingredient.getName()));
            quantityTextView.setText(String.valueOf(QuantityUtils.getIngredientQuantity(ingredient.getQuantity())));
            measureTextView.setText(QuantityUtils.getMeasureText(context, String.valueOf(ingredient.getMeasure())));
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
