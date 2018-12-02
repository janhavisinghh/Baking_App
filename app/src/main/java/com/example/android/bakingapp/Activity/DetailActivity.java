package com.example.android.bakingapp.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.Fragment.DetailListFragment;
import com.example.android.bakingapp.Fragment.StepsFragment;
import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements DetailListFragment.OnStepClickListener {
    private ArrayList<Steps> stepsList;
    private ArrayList<Ingredients> ingredientsList;
    private int position;
    private static final String KEY_PARCEL_INGREDIENTS_LIST = "ingredients_list";
    private static final String KEY_PARCEL_STEPS_LIST = "steps_list";

    private String name;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if(findViewById(R.id.step_activity)!=null){
            mTwoPane = true;
        }
        else mTwoPane=false;

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        stepsList = new ArrayList<Steps>();
        ingredientsList = new ArrayList<Ingredients>();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        stepsList = (ArrayList<Steps>) args.getSerializable("steps");
        ingredientsList = (ArrayList<Ingredients>) args.getSerializable("ingredients");
        position = getIntent().getExtras().getInt("position");
        name = (String) args.getString("name");

        DetailListFragment detailListFragment = new DetailListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putSerializable("steps", stepsList);
        bundle.putSerializable("ingredients", ingredientsList);
        bundle.putString("name", name);
        detailListFragment.setArguments(bundle);

        setTitle(name);

        fragmentManager.beginTransaction()
                .add(R.id.recipes_details_list_fragment, detailListFragment)
                .commit();
    }

    @Override
    public void OnStepSelected (int position){
        Bundle bundle = new Bundle();
        bundle.putSerializable("step_id", stepsList.get(position).getId());
        bundle.putSerializable("short_desc", stepsList.get(position).getShortDescription());
        bundle.putSerializable("description", stepsList.get(position).getDescription());
        bundle.putSerializable("video_url", stepsList.get(position).getVideoURL());
        bundle.putSerializable("thumbnail_url", stepsList.get(position).getThumbnailURL());

        if(mTwoPane) {
            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_activity, stepsFragment)
                    .commit();


        }
        else{
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra("bundle", bundle);
            this.startActivity(intent);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int index = -1;
        int itemThatWasClickedId = item.getItemId();
        if(itemThatWasClickedId== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

}
