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
import com.diegomalone.bakingapp.ui.fragment.RecipeStepFragment;

import butterknife.BindView;

import static com.diegomalone.bakingapp.utils.FlowController.RECIPE_EXTRA;
import static com.diegomalone.bakingapp.utils.FlowController.STEP_EXTRA;

/**
 * Created by malone on 12/02/18.
 */

public class RecipeStepActivity extends BaseActivity implements PreviousNextClickListener {

    public static final String STEP_DETAIL_FRAGMENT_TAG = "stepDetailFragmentTag";

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecipeStepFragment recipeStepFragment;

    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        bindViews();
        setupToolbar(toolbar, true);

        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(RECIPE_EXTRA);
        Step step = intent.getParcelableExtra(STEP_EXTRA);

        if (savedInstanceState == null) {
            recipeStepFragment = RecipeStepFragment.newInstance(recipe, step);

            showFragment();
        } else {
            recipeStepFragment = (RecipeStepFragment) getSupportFragmentManager().findFragmentByTag(STEP_DETAIL_FRAGMENT_TAG);
        }
    }

    @Override
    public void showStep(Step step) {
        recipeStepFragment = RecipeStepFragment.newInstance(recipe, step);

        showFragment();
    }

    private void showFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, recipeStepFragment, STEP_DETAIL_FRAGMENT_TAG)
                .commit();
    }
}
