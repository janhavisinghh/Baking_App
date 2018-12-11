package com.example.android.bakingapp.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.Fragment.StepsFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.ActivityStepBinding;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {
    ActivityStepBinding binding;
    private String step_id;
    private String short_desc;
    private String description;
    private String video_url;
    private String thumbnail_url;
    private int position;
    private ArrayList<Steps> steps;
    private Boolean mTwoPane;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_step);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        step_id = (String) args.getSerializable("step_id");
        short_desc = (String) args.getSerializable("short_desc");
        description = (String) args.getSerializable("description");
        video_url = (String) args.getSerializable("video_url");
        position = args.getInt("position");
        thumbnail_url = (String) args.getSerializable("thumbnail_url");
        steps = (ArrayList<Steps>) args.getSerializable("stepsList");
        mTwoPane = args.getBoolean("mtwoPane");
        StepsFragment stepsFragment = new StepsFragment();

        setTitle(short_desc);


        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putSerializable("step_id", step_id);
        bundle.putSerializable("short_desc", short_desc);
        bundle.putSerializable("description", description);
        bundle.putSerializable("video_url", video_url);
        bundle.putSerializable("thumbnail_url", thumbnail_url);
        bundle.putInt("position", position);
        bundle.putSerializable("stepsList", steps);
        bundle.putBoolean("mtwoPane", mTwoPane);
        stepsFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.step_activity, stepsFragment)
                .commit();


    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int index = -1;
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }


}


