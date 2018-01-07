package com.diegomalone.bakingapp.network.retrofit;

import com.diegomalone.bakingapp.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Diego Malone on 06/01/18.
 */

public interface BackingRestClient {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();

}
