package com.example.android.bakingapp.Fragment;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android.bakingapp.Activity.FullscreenActivity;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.ViewManager.ExoPlayerViewManager;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.StepFragmentBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;


public class StepsFragment extends Fragment {
    private String step_id;
    private String short_desc;
    private String description;
    private String video_url;
    private String thumbnail_url;
    private ArrayList<Steps> stepsList;
    private int position;
    private Boolean mTwopane;

    public ExoPlayerViewManager mExoPlayerViewManager;
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    public static final String KEY_VIDEO_URL_FULLSCREEN = "video_url_fullscreen";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private Dialog mFullScreenDialog;
    private int mResumeWindow;
    private long mResumePosition;
    StepFragmentBinding binding;
    public StepsFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
        final View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        stepsList=new ArrayList<>();
        binding = StepFragmentBinding.bind(rootView);
        step_id = (String)getArguments().getSerializable("step_id");
        short_desc = (String)getArguments().getSerializable("short_desc");
        description = (String)getArguments().getSerializable("description");
        video_url = (String)getArguments().getSerializable("video_url");
        thumbnail_url = (String)getArguments().getSerializable("thumbnail_url");
        stepsList = (ArrayList<Steps>) getArguments().getSerializable("stepsList");
        mTwopane = getArguments().getBoolean("mtwoPane");
        position =  getArguments().getInt("position");
        mExoPlayerViewManager = ExoPlayerViewManager.getInstance(video_url, getContext());
        FrameLayout fullscreenLayout = rootView.findViewById(R.id.exo_fullscreen_button);

        if(StepsFragment.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT|| mTwopane==true)
        {
        if(video_url.equals("")){
            binding.playerView.setVisibility(INVISIBLE);
            binding.defaultMariyam.setVisibility(View.VISIBLE);
        }
        else{
            binding.playerView.setVisibility(View.VISIBLE);
            binding.defaultMariyam.setVisibility(View.INVISIBLE);
            initializePlayer(Uri.parse(video_url));
            }
        }
        else
         {
            Intent intent = new Intent(getActivity(), FullscreenActivity.class);
            intent.putExtra(KEY_VIDEO_URL_FULLSCREEN, video_url);
            startActivity(intent);
        }

        fullscreenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra(KEY_VIDEO_URL_FULLSCREEN, video_url);
                startActivity(intent);
            }
        });


        binding.stepDescTv.setText(description);
        binding.prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>0)
                    position--;
                else
                    position = stepsList.size()-1;

                binding.stepDescTv.setText(stepsList.get(position).getDescription());
                onDestroy();
                if(stepsList.get(position).getVideoURL().equals("")){
                    binding.playerView.setVisibility(INVISIBLE);
                    binding.defaultMariyam.setVisibility(View.VISIBLE);
                    }
                else{
                    binding.playerView.setVisibility(View.VISIBLE);
                    binding.defaultMariyam.setVisibility(View.INVISIBLE);
                    initializePlayer(Uri.parse(stepsList.get(position).getVideoURL()));
                }

    }
        });
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<stepsList.size()-1)
                    position ++;
                else
                    position = 0;

                binding.stepDescTv.setText(stepsList.get(position).getDescription());
                onDestroy();
                if(stepsList.get(position).getVideoURL().equals("")){
                    binding.playerView.setVisibility(INVISIBLE);
                    binding.defaultMariyam.setVisibility(View.VISIBLE);
                }
                else{
                    binding.playerView.setVisibility(View.VISIBLE);
                    binding.defaultMariyam.setVisibility(View.INVISIBLE);
                    initializePlayer(Uri.parse(stepsList.get(position).getVideoURL()));
                }
            }
        });
        return rootView;
    }
    private void initializePlayer(Uri parse) {
        if(simpleExoPlayer== null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            binding.playerView.setPlayer(simpleExoPlayer);
            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(parse, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);

        }
    }
    private void releasePlayer() {
        if(simpleExoPlayer!=null){
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;}
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(simpleExoPlayer!=null)
        releasePlayer();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
