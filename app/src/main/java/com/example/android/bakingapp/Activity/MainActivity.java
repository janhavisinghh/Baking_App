package com.example.android.bakingapp.Activity;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.bakingapp.Adapter.RecipeAdapter;
import com.example.android.bakingapp.Data.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.api.GetDataService;
import com.example.android.bakingapp.api.RetrofitClientInstance;
import com.example.android.bakingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecipeAdapter adapter;
    private static final int SPAN_COUNT = 2;
    private ArrayList<Recipe> recipesList;
    private static final String KEY_PARCEL_RECIPE_LIST = "recipes_list";
    private int mPosition = RecyclerView.NO_POSITION;
    ActivityMainBinding binding;
    public static final String KEY_INGREDIENT = "ingredient";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        recipesList = new ArrayList<>();

        if(MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        }
        binding.recyclerView.setHasFixedSize(true);
        adapter = new RecipeAdapter(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe position) {
            }
        });
        binding.recyclerView.setAdapter(adapter);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<ArrayList<Recipe>> call = service.getAllRecipe();

        call.enqueue(new Callback<ArrayList<Recipe>>()
        {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response)
            {
                recipesList = response.body();
                adapter.setRecipes(recipesList);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_PARCEL_RECIPE_LIST, recipesList);
        super.onSaveInstanceState(outState);
    }

}
