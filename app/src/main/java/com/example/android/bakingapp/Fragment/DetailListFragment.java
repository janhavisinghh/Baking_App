package com.example.android.bakingapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.android.bakingapp.Activity.DetailActivity;
import com.example.android.bakingapp.Activity.IngredientsActivity;
import com.example.android.bakingapp.Adapter.DetailsAdapter;
import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

public class DetailListFragment extends Fragment implements DetailsAdapter.OnItemClickListener{
    private int position;
    private ArrayList<Steps> steps;
    private ArrayList<Ingredients> ingredientsList;
    DetailsAdapter adapter;
    private RelativeLayout relativeLayout;
    private TextView ingredients_tv;

    public DetailListFragment(){}

    OnStepClickListener mCallback;

    public interface OnStepClickListener{
        void OnStepSelected(int position);
    }

    @Override
    public void onItemClick(int position) {
        mCallback.OnStepSelected(position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        final View rootView = inflater.inflate(R.layout.detail_list, container, false);
        steps = new ArrayList<>();
        ingredientsList = new ArrayList<>();
        steps = (ArrayList<Steps>) getArguments().getSerializable("steps");
        ingredientsList = (ArrayList<Ingredients>) getArguments().getSerializable("ingredients");
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relative_layout_ingre);

        position = getArguments().getInt("position");

        adapter = new DetailsAdapter(this);
        ingredients_tv = (TextView) rootView.findViewById(R.id.ingredients_sd_tv);
        ingredients_tv.setText("This recipe has " +ingredientsList.size() +" ingredients!");
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.details_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setSteps(steps);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),IngredientsActivity.class), 0);
            }
        });


        return rootView;
    }


}
