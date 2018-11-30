package com.example.android.bakingapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.android.bakingapp.Adapter.DetailsAdapter;
import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

public class DetailListFragment extends Fragment {
    private int position;
    private ArrayList<Steps> steps;
    private ArrayList<Ingredients> ingredientsList;
    DetailsAdapter adapter;
    private TextView ingredients_tv;

    public DetailListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        final View rootView = inflater.inflate(R.layout.detail_list, container, false);
        steps = new ArrayList<>();
        ingredientsList = new ArrayList<>();
        steps = (ArrayList<Steps>) getArguments().getSerializable("steps");
        ingredientsList = (ArrayList<Ingredients>) getArguments().getSerializable("ingredients");

        position = getArguments().getInt("position");

        adapter = new DetailsAdapter(new DetailsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Steps position) {
            }
        });
        ingredients_tv = (TextView) rootView.findViewById(R.id.ingredients_sd_tv);
        ingredients_tv.setText("This recipe has " +ingredientsList.size() +" ingredients!");
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.details_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setSteps(steps);

        return rootView;
    }

}
