package com.example.android.bakingapp.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
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
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;


public class StepsFragment extends Fragment {
    private String description;
    private String video_url;
    private ArrayList<Steps> stepsList;
    private int position;
    long positionPlayer;
    private FrameLayout fullscreenLayout;
    public static final String KEY_DESCRIPTION = "description";

    private Boolean mTwopane;

    public ExoPlayerViewManager mExoPlayerViewManager;
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    public static final String KEY_VIDEO_URL_FULLSCREEN = "video_url_fullscreen";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    public static final String MEDIA_POS = "MEDIA_POSITION";

    private SimpleExoPlayer simpleExoPlayer;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private Dialog mFullScreenDialog;
    private int mResumeWindow;
    private long mResumePosition;
    private MediaSessionCompat mediaSessionCompat;
    StepFragmentBinding binding;
    boolean playWhenReady;

    public StepsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)

    {

        final View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        stepsList = new ArrayList<>();
        binding = StepFragmentBinding.bind(rootView);
        description = (String) getArguments().getSerializable("description");
        video_url = (String) getArguments().getSerializable("video_url");
        stepsList = (ArrayList<Steps>) getArguments().getSerializable("stepsList");
        mTwopane = getArguments().getBoolean("mtwoPane");
        position = getArguments().getInt("position");



        fullscreenLayout = rootView.findViewById(R.id.exo_fullscreen_button);
            if (video_url.equals("")) {
                binding.playerView.setVisibility(INVISIBLE);
                binding.defaultMariyam.setVisibility(View.VISIBLE);
            } else {
                if (savedInstanceState != null) {
                    positionPlayer = savedInstanceState.getLong(MEDIA_POS, 0);
                }

                binding.playerView.setVisibility(View.VISIBLE);
                binding.defaultMariyam.setVisibility(View.INVISIBLE);
                setupPlayerView(binding.playerView, video_url);
            }



        if(savedInstanceState!=null) {
            description = (String) savedInstanceState.getSerializable(KEY_DESCRIPTION);
        }
        else
        binding.stepDescTv.setText(description);

        binding.prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0)
                    position--;
                else
                    position = stepsList.size() - 1;

                binding.stepDescTv.setText(stepsList.get(position).getDescription());
                onStop();
                if (stepsList.get(position).getVideoURL().equals("")) {
                    binding.playerView.setVisibility(INVISIBLE);
                    binding.defaultMariyam.setVisibility(View.VISIBLE);
                } else {
                    binding.playerView.setVisibility(View.VISIBLE);
                    binding.defaultMariyam.setVisibility(View.INVISIBLE);
                    setupPlayerView(binding.playerView, stepsList.get(position).getVideoURL());
                }

            }
        });
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < stepsList.size() - 1)
                    position++;
                else
                    position = 0;

                binding.stepDescTv.setText(stepsList.get(position).getDescription());
                onStop();
                if (stepsList.get(position).getVideoURL().equals("")) {
                    binding.playerView.setVisibility(INVISIBLE);
                    binding.defaultMariyam.setVisibility(View.VISIBLE);
                } else {
                    binding.playerView.setVisibility(View.VISIBLE);
                    binding.defaultMariyam.setVisibility(View.INVISIBLE);
                    setupPlayerView(binding.playerView, stepsList.get(position).getVideoURL());


                }
            }
        });
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mExoPlayerViewManager != null && !(stepsList.get(position) == null || stepsList.get(position).getVideoURL().equals(""))) {
            if (!isVisibleToUser) {
                ExoPlayerViewManager.getInstance(video_url,getContext()).goToBackground();
            } else {
                ExoPlayerViewManager.getInstance(video_url,getContext()).prepareExoPlayer(binding.playerView);
                ExoPlayerViewManager.getInstance(video_url,getContext()).goToForeground();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stepsList.get(position) != null && !stepsList.get(position).getVideoURL().equals("")) {
            ExoPlayerViewManager.getInstance(stepsList.get(position).getVideoURL(),getContext()).prepareExoPlayer(binding.playerView);
            if (getUserVisibleHint()) ExoPlayerViewManager.getInstance(video_url,getContext()).goToForeground();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerViewManager.getInstance(video_url,getContext()).goToBackground();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!getActivity().isChangingConfigurations()) {
            ExoPlayerViewManager.getInstance(video_url,getContext()).releaseVideoPlayer();
        }
    }
    private void setupPlayerView(final PlayerView videoView, final String videoUrl) {
        ExoPlayerViewManager.getInstance(videoUrl,getContext()).prepareExoPlayer(videoView);
        ExoPlayerViewManager.getInstance(videoUrl,getContext()).goToForeground();


        fullscreenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra(KEY_VIDEO_URL_FULLSCREEN, video_url);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(KEY_DESCRIPTION, description);
    }

}


