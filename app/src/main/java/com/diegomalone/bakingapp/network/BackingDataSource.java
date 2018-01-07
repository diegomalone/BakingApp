package com.diegomalone.bakingapp.network;

import android.support.annotation.NonNull;

import com.diegomalone.bakingapp.BuildConfig;
import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.network.retrofit.BackingRestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Diego Malone on 06/01/18.
 */

public class BackingDataSource {

    private static BackingDataSource instance;

    private BackingRestClient restClient;

    public static BackingDataSource getInstance() {
        if (instance == null) {
            instance = new BackingDataSource();
        }

        return instance;
    }

    private BackingDataSource() {
        OkHttpClient okHttpClient = createOkHttpClient();
        Gson gson = createGson();

        Retrofit retrofit = createRetrofit(okHttpClient, gson, BuildConfig.API_URL);

        restClient = retrofit.create(BackingRestClient.class);
    }

    public Observable<List<Recipe>> getRecipeList() {
        return restClient.getRecipes();
    }

    @NonNull
    private Retrofit createRetrofit(OkHttpClient okHttpClient, Gson gson, String apiUrl) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(apiUrl)
                .client(okHttpClient)
                .build();
    }

    @NonNull
    private Gson createGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @NonNull
    private OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
