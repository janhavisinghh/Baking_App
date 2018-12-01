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
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

public class StepsFragment extends Fragment {
    private String step_id;
    private String short_desc;
    private String description;
    private String video_url;
    private String thumbnail_url;
    private TextView video_thumbnail;
    private int position;
    public StepsFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        final View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        step_id = (String)getArguments().getSerializable("step_id");
        short_desc = (String)getArguments().getSerializable("short_desc");
        description = (String)getArguments().getSerializable("description");
        video_url = (String)getArguments().getSerializable("video_url");
        thumbnail_url = (String)getArguments().getSerializable("thumbnail_url");
        video_thumbnail = (TextView) rootView.findViewById(R.id.step_video);
        video_thumbnail.setText(step_id);

        return rootView;
    }
}
