package com.diegomalone.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.model.Step;
import com.diegomalone.bakingapp.ui.events.PreviousNextClickListener;
import com.diegomalone.bakingapp.ui.events.StepClickListener;
import com.diegomalone.bakingapp.ui.fragment.RecipeStepFragment;
import com.diegomalone.bakingapp.ui.fragment.RecipeStepListFragment;
import com.diegomalone.bakingapp.utils.FlowController;

import butterknife.BindView;

import static com.diegomalone.bakingapp.utils.FlowController.RECIPE_EXTRA;

/**
 * Created by Diego Malone on 21/01/18.
 */

public class RecipeStepListActivity extends BaseActivity implements StepClickListener, PreviousNextClickListener {

    public static final String STEP_LIST_FRAGMENT_TAG = "stepListFragmentTag";
    public static final String STEP_DETAIL_FRAGMENT_TAG = "stepDetailFragmentTag";

    public static final String CURRENT_STEP_KEY = "currentStep";

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecipeStepListFragment recipeStepListFragment;
    private RecipeStepFragment recipeStepFragment;

    private Boolean isPhone;

    private Recipe recipe;
    private Step currentStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_list);
        bindViews();
        setupToolbar(toolbar, true);

        isPhone = getResources().getBoolean(R.bool.is_phone);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(RECIPE_EXTRA);

        if (savedInstanceState == null) {
            recipeStepListFragment = RecipeStepListFragment.newInstance(recipe);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, recipeStepListFragment, STEP_LIST_FRAGMENT_TAG)
                    .commit();
        } else {
            recipeStepListFragment = (RecipeStepListFragment) getSupportFragmentManager().findFragmentByTag(STEP_LIST_FRAGMENT_TAG);
        }

        if (!isPhone) {
            if (savedInstanceState != null) {
                currentStep = savedInstanceState.getParcelable(CURRENT_STEP_KEY);
            }

            if (currentStep == null) {
                if (recipe.getStepList() != null && !recipe.getStepList().isEmpty()) {
                    currentStep = recipe.getStepList().get(0);
                }
            }

            showStep(currentStep);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_STEP_KEY, currentStep);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepClick(Step step) {
        showStep(step);
        currentStep = step;
    }

    @Override
    public void showStep(Step step) {
        if (step == null) return;

        if (isPhone) {
            FlowController.openRecipeStepScreen(this, recipe, step);
        } else {
            recipeStepFragment = RecipeStepFragment.newInstance(recipe, step);
            recipeStepListFragment.setStepSelected(step);

            showFragment();
        }
    }

    private void showFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentDetail, recipeStepFragment, STEP_DETAIL_FRAGMENT_TAG)
                .commit();
    }
}
