package com.example.android.bakingapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;

public class IngredientsFragment extends Fragment {
//    private String step_id;
//    private String short_desc;
//    private String description;
//    private String video_url;
//    private String thumbnail_url;
    public IngredientsFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        final View rootView = inflater.inflate(R.layout.ingredient_fragment, container, false);
//        step_id = (String)getArguments().getSerializable("step_id");
//        short_desc = (String)getArguments().getSerializable("short_desc");
//        description = (String)getArguments().getSerializable("description");
//        video_url = (String)getArguments().getSerializable("video_url");
//        thumbnail_url = (String)getArguments().getSerializable("thumbnail_url");
//
//

        return rootView;
    }
}

