package com.example.android.bakingapp.api;

import com.example.android.bakingapp.Data.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("baking.json")
    Call<ArrayList<Recipe>> getAllRecipe();
}
