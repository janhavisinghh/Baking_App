package com.example.android.bakingapp.Activity;

import android.app.ActionBar;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.Fragment.DetailListFragment;
import com.example.android.bakingapp.Fragment.StepsFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Widget.IngredientsWidgetProvider;
import com.example.android.bakingapp.Widget.ListWidgetService;
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
        if(savedInstanceState != null) {
            stepsList = (ArrayList<Steps>) savedInstanceState.getSerializable(KEY_PARCEL_STEPS_LIST);
            ingredientsList = (ArrayList<Ingredients>) savedInstanceState.getSerializable(KEY_PARCEL_INGREDIENTS_LIST);
        }
        if(savedInstanceState==null){
            stepsList = (ArrayList<Steps>) args.getSerializable("steps");
        ingredientsList = (ArrayList<Ingredients>) args.getSerializable("ingredients");}
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
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putSerializable(KEY_PARCEL_STEPS_LIST, stepsList);
        currentState.putSerializable(KEY_PARCEL_INGREDIENTS_LIST, ingredientsList);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_save:
                Toast.makeText(getApplicationContext(),"Saved!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ListWidgetService.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ingredients", ingredientsList);
                bundle.putString("name", name);
                bundle.putInt("position",position);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] appWidgetIds = AppWidgetManager.getInstance(getApplication())
                        .getAppWidgetIds(new ComponentName(getApplication(), ListWidgetService.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                intent.putExtra("bundle", bundle);
                sendBroadcast(intent);
                return true;
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
