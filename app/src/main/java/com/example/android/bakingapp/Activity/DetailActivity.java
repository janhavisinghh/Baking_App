package com.example.android.bakingapp.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.Fragment.DetailListFragment;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailListFragment.OnStepClickListener{
    private ArrayList<Steps> stepsList;
    private ArrayList<Ingredients> ingredientsList;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        stepsList = new ArrayList<>();
        ingredientsList = new ArrayList<>();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        stepsList = (ArrayList<Steps>) args.getSerializable("steps");
        ingredientsList = (ArrayList<Ingredients>) args.getSerializable("ingredients");
        position = getIntent().getExtras().getInt("position");

        DetailListFragment detailListFragment = new DetailListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable("steps", stepsList);
        bundle.putSerializable("ingredients", ingredientsList);
        detailListFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.recipes_details_list_fragment,detailListFragment)
                .commit();

    }

    @Override
    public void OnStepSelected(int position) {
//        Toast.makeText(getApplicationContext(),steps.getDescription(),Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("step_id", stepsList.get(position).getId());
        bundle.putSerializable("short_desc", stepsList.get(position).getShortDescription());
        bundle.putSerializable("description", stepsList.get(position).getDescription());
        bundle.putSerializable("video_url", stepsList.get(position).getVideoURL());
        bundle.putSerializable("thumbnail_url", stepsList.get(position).getThumbnailURL());
        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra("bundle", bundle);
        this.startActivity(intent);
    }
}
