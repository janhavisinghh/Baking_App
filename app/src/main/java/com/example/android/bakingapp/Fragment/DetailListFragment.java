package com.example.android.bakingapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.android.bakingapp.Adapter.DetailsAdapter;
import com.example.android.bakingapp.Adapter.IngredientAdapter;
import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

public class DetailListFragment extends Fragment implements DetailsAdapter.OnItemClickListener {
    private int position;
    private ArrayList<Steps> steps;
    private ArrayList<Ingredients> ingredientsList;
    DetailsAdapter adapter;
    IngredientAdapter ingredientAdapter;
    private String name;
    private static final String KEY_PARCEL_INGREDIENTS_LIST = "ingredients_list";
    private static final String KEY_PARCEL_STEPS_LIST = "steps_list";
    private TextView name_tv;

    public DetailListFragment() {
    }

    OnStepClickListener mCallback;

    public interface OnStepClickListener {
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

    {final View rootView = inflater.inflate(R.layout.detail_list, container, false);



            steps = new ArrayList<>();
            ingredientsList = new ArrayList<>();
            steps = (ArrayList<Steps>) getArguments().getSerializable("steps");
            ingredientsList = (ArrayList<Ingredients>) getArguments().getSerializable("ingredients");
            name = (String) getArguments().getString("name");
            name_tv = (TextView) rootView.findViewById(R.id.detail_name_tv);
            name_tv.setText(name);


            position = getArguments().getInt("position");

        if(savedInstanceState != null) {
            steps = (ArrayList<Steps>) savedInstanceState.getSerializable(KEY_PARCEL_STEPS_LIST);
            ingredientsList = (ArrayList<Ingredients>) savedInstanceState.getSerializable(KEY_PARCEL_INGREDIENTS_LIST);
            }

            adapter = new DetailsAdapter(this);
            ingredientAdapter = new IngredientAdapter();
            final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.details_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            adapter.setSteps(steps);

            final RecyclerView recyclerView1 = (RecyclerView) rootView.findViewById(R.id.ingredients_rv);
            recyclerView1.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView1.setHasFixedSize(true);
            recyclerView1.setAdapter(ingredientAdapter);
            ingredientAdapter.setSteps(ingredientsList);

        return rootView;

    }
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(KEY_PARCEL_STEPS_LIST, steps);
        currentState.putSerializable(KEY_PARCEL_INGREDIENTS_LIST, ingredientsList);
    }


}
