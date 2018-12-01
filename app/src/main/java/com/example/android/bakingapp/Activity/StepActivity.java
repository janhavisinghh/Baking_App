package com.example.android.bakingapp.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.Fragment.DetailListFragment;
import com.example.android.bakingapp.Fragment.StepsFragment;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {
    private String step_id;
    private String short_desc;
    private String description;
    private String video_url;
    private String thumbnail_url;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("bundle");
        step_id = (String)args.getSerializable("step_id");
        short_desc = (String)args.getSerializable("short_desc");
        description = (String)args.getSerializable("description");
        video_url = (String)args.getSerializable("video_url");
        position = (int)args.getInt("position");
        thumbnail_url = (String)args.getSerializable("thumbnail_url");
        StepsFragment stepsFragment = new StepsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putSerializable("step_id", step_id);
        bundle.putSerializable("short_desc", short_desc);
        bundle.putSerializable("description", description);
        bundle.putSerializable("video_url", video_url);
        bundle.putSerializable("thumbnail_url", thumbnail_url);
        stepsFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.step_activity,stepsFragment)
                .commit();
    }
}
