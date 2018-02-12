package com.diegomalone.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.model.Step;
import com.diegomalone.bakingapp.ui.events.StepClickListener;
import com.diegomalone.bakingapp.ui.fragment.RecipeStepListFragment;
import com.diegomalone.bakingapp.utils.ToastUtils;

import butterknife.BindView;

import static com.diegomalone.bakingapp.utils.FlowController.RECIPE_EXTRA;

/**
 * Created by Diego Malone on 21/01/18.
 */

public class RecipeStepListActivity extends BaseActivity implements StepClickListener {

    public static final String STEP_LIST_FRAGMENT_TAG = "stepListFragmentTag";

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecipeStepListFragment recipeStepListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_list);
        bindViews();
        setupToolbar(toolbar, true);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(RECIPE_EXTRA);

        recipeStepListFragment = RecipeStepListFragment.newInstance(recipe);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, recipeStepListFragment, STEP_LIST_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onStepClick(Step step) {
        ToastUtils.showToast(this, step.getShortDescription());
    }
}
