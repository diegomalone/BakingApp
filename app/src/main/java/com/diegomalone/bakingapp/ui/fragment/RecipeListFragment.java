package com.diegomalone.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.diegomalone.bakingapp.R;
import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.network.BackingDataSource;
import com.diegomalone.bakingapp.ui.adapter.RecipeListAdapter;
import com.diegomalone.bakingapp.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeListFragment extends Fragment {

    public static final String LIST_KEY = "listKey";
    public static final int GRID_SPAN_COUNT = 3;

    private ArrayList<Recipe> recipeList = new ArrayList<>();

    private RecyclerView recipeListRecyclerView;
    private RecipeListAdapter recipeListAdapter;

    private BackingDataSource backingDataSource;
    private boolean isPhone = true;

    public RecipeListFragment() {
        backingDataSource = BackingDataSource.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getContext() != null) {
            isPhone = getContext().getResources().getBoolean(R.bool.is_phone);
        }

        recipeListRecyclerView = view.findViewById(R.id.recipeListRecyclerView);

        initRecyclerView();

        if (savedInstanceState != null) {
            ArrayList<Recipe> recipeList = savedInstanceState.getParcelableArrayList(LIST_KEY);

            if (recipeList != null && !recipeList.isEmpty()) {
                updateRecipeList(recipeList);
            }
        }

        if (recipeList.isEmpty()) {
            loadRecipes();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(LIST_KEY, recipeList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recipe_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_reload:
                loadRecipes();
                return true;
            default:
                break;
        }

        return false;
    }

    public void loadRecipes() {
        backingDataSource.getRecipeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeList -> {
                            Timber.i("Recipe list received");
                            ToastUtils.showToast(getContext(), getString(R.string.success_fetching_recipe_list));
                            updateRecipeList(recipeList);
                        }, error -> Timber.e(error)
                );
    }

    public void updateRecipeList(@NonNull List<Recipe> recipeList) {
        this.recipeList.clear();
        this.recipeList.addAll(recipeList);

        // TODO Remove test items from code
        this.recipeList.addAll(recipeList);

        if (recipeListAdapter != null) {
            recipeListAdapter.updateList(recipeList);
            recipeListAdapter.notifyDataSetChanged();
        }
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        recipeListRecyclerView.setLayoutManager(layoutManager);

        recipeListAdapter = new RecipeListAdapter(getContext(), null, recipe ->
                ToastUtils.showToast(getContext(), recipe.getName())
        );
        recipeListRecyclerView.setAdapter(recipeListAdapter);
    }

    @NonNull
    private RecyclerView.LayoutManager getLayoutManager() {
        RecyclerView.LayoutManager layoutManager;

        if (isPhone) {
            layoutManager = new LinearLayoutManager(getContext());
        } else {
            layoutManager = new GridLayoutManager(getContext(), GRID_SPAN_COUNT);
        }

        return layoutManager;
    }
}
