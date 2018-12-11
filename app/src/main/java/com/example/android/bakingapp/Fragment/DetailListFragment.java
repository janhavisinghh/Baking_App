package com.example.android.bakingapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Adapter.DetailsAdapter;
import com.example.android.bakingapp.Adapter.IngredientAdapter;
import com.example.android.bakingapp.Data.Ingredients;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.DetailListBinding;

import java.util.ArrayList;

public class DetailListFragment extends Fragment implements DetailsAdapter.OnItemClickListener {
    private static final String KEY_PARCEL_INGREDIENTS_LIST = "ingredients_list";
    private static final String KEY_PARCEL_STEPS_LIST = "steps_list";
    DetailsAdapter adapter;
    IngredientAdapter ingredientAdapter;
    DetailListBinding binding;
    ArrayList<Ingredients> recipeIngredientsForWidgets = new ArrayList<>();
    OnStepClickListener mCallback;
    private int position;
    private ArrayList<Steps> steps;
    private ArrayList<Ingredients> ingredientsList;
    private String name;
    private TextView name_tv;

    public DetailListFragment() {
    }

    /**
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        mCallback.OnStepSelected(position);
    }

    /**
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Listener");
        }

    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        final View rootView = inflater.inflate(R.layout.detail_list, container, false);
        binding = DetailListBinding.bind(rootView);


        steps = new ArrayList<>();
        ingredientsList = new ArrayList<>();
        steps = (ArrayList<Steps>) getArguments().getSerializable("steps");
        ingredientsList = (ArrayList<Ingredients>) getArguments().getSerializable("ingredients");
        name = (String) getArguments().getString("name");
        binding.detailNameTv.setText(name);


        position = getArguments().getInt("position");

        if (savedInstanceState != null) {
            steps = (ArrayList<Steps>) savedInstanceState.getSerializable(KEY_PARCEL_STEPS_LIST);
            ingredientsList = (ArrayList<Ingredients>) savedInstanceState.getSerializable(KEY_PARCEL_INGREDIENTS_LIST);
        }


        adapter = new DetailsAdapter(this);
        ingredientAdapter = new IngredientAdapter();
        binding.detailsRv.setLayoutManager(new LinearLayoutManager(binding.detailsRv.getContext()));
        binding.detailsRv.setHasFixedSize(true);
        binding.detailsRv.setAdapter(adapter);
        adapter.setSteps(steps);

        binding.ingredientsRv.setLayoutManager(new LinearLayoutManager(binding.ingredientsRv.getContext()));
        binding.ingredientsRv.setHasFixedSize(true);
        binding.ingredientsRv.setAdapter(ingredientAdapter);
        ingredientAdapter.setSteps(ingredientsList);


        return rootView;

    }

    /**
     * @param currentState
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(KEY_PARCEL_STEPS_LIST, steps);
        currentState.putSerializable(KEY_PARCEL_INGREDIENTS_LIST, ingredientsList);
    }

    public interface OnStepClickListener {
        void OnStepSelected(int position);
    }


}
