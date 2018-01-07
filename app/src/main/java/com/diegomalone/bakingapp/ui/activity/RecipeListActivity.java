package com.diegomalone.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.network.BackingDataSource;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RecipeListActivity extends BaseActivity {

    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        bindViews();
        setSupportActionBar(toolbar);

        fab.setOnClickListener(view -> {
            loadRecipes();
        });
    }

    private void loadRecipes() {
        BackingDataSource backingDataSource = BackingDataSource.getInstance();

        backingDataSource.getRecipeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeList -> {
                    Timber.i("Recipe list received");
                    Timber.i(recipeList.toString());
                }, error -> {
                    Snackbar.make(coordinatorLayout, R.string.error_fetching_recipe_list, Snackbar.LENGTH_LONG)
                            .setAction(R.string.action_try_again, view -> loadRecipes()).show();
                    Timber.e(error);
                });
    }

}
