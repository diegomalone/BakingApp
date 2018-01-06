package com.diegomalone.bakingapp.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diegomalone.bakingapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeListActivityFragment extends Fragment {

    public RecipeListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }
}
