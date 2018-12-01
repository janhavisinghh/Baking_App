package com.example.android.bakingapp.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Fragment.IngredientsFragment;
import com.example.android.bakingapp.R;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        IngredientsFragment ingredientsFragment = new IngredientsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.ingredient_activity,ingredientsFragment)
                .commit();
    }
}
