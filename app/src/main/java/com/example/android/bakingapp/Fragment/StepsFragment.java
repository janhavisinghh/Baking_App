package com.example.android.bakingapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapter.DetailsAdapter;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

public class StepsFragment extends Fragment {
    public StepsFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        final View rootView = inflater.inflate(R.layout.detail_list, container, false);
//        steps = new ArrayList<>();
//        steps = (ArrayList<Steps>) getArguments().getSerializable("steps");
//        position = getArguments().getInt("position");
//
//        adapter = new DetailsAdapter(new DetailsAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Steps position) {
//            }
//        });
//
//
//        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.details_rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(adapter);
//        adapter.setSteps(steps);

        return rootView;
    }
}
