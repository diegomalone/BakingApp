package com.diegomalone.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.ui.fragment.RecipeListFragment;

import butterknife.BindView;

public class RecipeListActivity extends BaseActivity {

    public static final String FRAGMENT_TAG = "recipeListFragment";

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RecipeListFragment recipeListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        bindViews();
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            recipeListFragment = new RecipeListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, recipeListFragment, FRAGMENT_TAG)
                    .commit();
        } else {
            recipeListFragment = (RecipeListFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }
    }
}
