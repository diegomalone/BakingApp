package com.diegomalone.bakingapp.ui.fragment;

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
import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.network.BackingDataSource;
import com.diegomalone.bakingapp.ui.adapter.RecipeListAdapter;

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

    private ArrayList<Recipe> recipeList = new ArrayList<>();

    private RecyclerView recipeListRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecipeListAdapter recipeListAdapter;

    private BackingDataSource backingDataSource;

    public RecipeListFragment() {
        backingDataSource = BackingDataSource.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    public void loadRecipes() {
        backingDataSource.getRecipeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeList -> {
                            Timber.i("Recipe list received");
                            updateRecipeList(recipeList);
                        }, error -> Timber.e(error)
                );
    }

    public void updateRecipeList(@NonNull List<Recipe> recipeList) {
        this.recipeList.clear();
        this.recipeList.addAll(recipeList);

        if (recipeListAdapter != null) {
            recipeListAdapter.updateList(recipeList);
            recipeListAdapter.notifyDataSetChanged();
        }
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext());
        recipeListRecyclerView.setLayoutManager(layoutManager);

        recipeListAdapter = new RecipeListAdapter(getContext(), null);
        recipeListRecyclerView.setAdapter(recipeListAdapter);
    }
}
