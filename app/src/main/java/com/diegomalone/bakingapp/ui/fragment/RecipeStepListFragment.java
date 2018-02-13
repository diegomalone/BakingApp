package com.diegomalone.bakingapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Ingredient;
import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.model.Step;
import com.diegomalone.bakingapp.ui.adapter.RecipeContentListAdapter;
import com.diegomalone.bakingapp.ui.events.StepClickListener;

import java.util.ArrayList;

/**
 * Created by malone on 12/02/18.
 */

public class RecipeStepListFragment extends Fragment {

    public static final String RECIPE_KEY = "recipeKey";
    public static final String INGREDIENT_LIST_KEY = "ingredientListKey";
    public static final String STEP_LIST_KEY = "stepListKey";

    private ArrayList<Step> stepList = new ArrayList<>();
    private ArrayList<Ingredient> ingredientList = new ArrayList<>();
    private Recipe recipe;

    private RecyclerView stepListRecyclerView;
    private RecipeContentListAdapter recipeContentListAdapter;

    private StepClickListener clickCallback;

    private Step selectedStep;

    public static RecipeStepListFragment newInstance(Recipe recipe) {
        RecipeStepListFragment fragment = new RecipeStepListFragment();

        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        ArrayList<Step> stepList = new ArrayList<>();

        ingredientList.addAll(recipe.getIngredientList());
        stepList.addAll(recipe.getStepList());

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENT_LIST_KEY, ingredientList);
        bundle.putParcelableArrayList(STEP_LIST_KEY, stepList);
        bundle.putParcelable(RECIPE_KEY, recipe);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            clickCallback = (StepClickListener) getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement " + StepClickListener.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stepListRecyclerView = view.findViewById(R.id.stepListRecyclerView);

        Bundle args = getArguments();

        if (args != null) {
            ingredientList = args.getParcelableArrayList(INGREDIENT_LIST_KEY);
            stepList = args.getParcelableArrayList(STEP_LIST_KEY);
            recipe = args.getParcelable(RECIPE_KEY);
        }

        initRecyclerView();

        setTitle();

        setStepSelected(selectedStep);
    }

    public void setStepSelected(Step step) {
        selectedStep = step;

        if (recipeContentListAdapter != null) {
            recipeContentListAdapter.setStepSelected(step);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(INGREDIENT_LIST_KEY, ingredientList);
        outState.putParcelableArrayList(STEP_LIST_KEY, stepList);
        outState.putParcelable(RECIPE_KEY, recipe);
        super.onSaveInstanceState(outState);
    }

    private void setTitle() {
        if (getActivity() != null) {
            getActivity().setTitle(recipe.getName());
        }
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        stepListRecyclerView.setLayoutManager(layoutManager);

        recipeContentListAdapter = new RecipeContentListAdapter(getContext(), ingredientList, stepList, clickCallback);
        stepListRecyclerView.setAdapter(recipeContentListAdapter);
    }
}
