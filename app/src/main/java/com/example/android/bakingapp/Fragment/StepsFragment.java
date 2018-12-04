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
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;


public class StepsFragment extends Fragment implements ExoPlayer.EventListener{
    private String step_id;
    private String short_desc;
    private String description;
    private String video_url;
    private String thumbnail_url;
    private ArrayList<Steps> stepsList;
    private int position;
    long positionPlayer;
    private PlaybackStateCompat.Builder playbackBuilder;
    public static final String KEY_PLAY_WHEN_READY="playWhenReady";

    private Boolean mTwopane;

    public ExoPlayerViewManager mExoPlayerViewManager;
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    public static final String KEY_VIDEO_URL_FULLSCREEN = "video_url_fullscreen";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    public static final String MEDIA_POS = "MEDIA_POSITION";

    private SimpleExoPlayerView mExoPlayerView;
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
        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
        }
        if(StepsFragment.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT|| mTwopane==true)
        {
        if(video_url.equals("")){
            binding.playerView.setVisibility(INVISIBLE);
            binding.defaultMariyam.setVisibility(View.VISIBLE);
        }
        else{if (savedInstanceState != null) {
            //resuming by seeking to the last position
            positionPlayer = savedInstanceState.getLong(MEDIA_POS);
        }
            initializeMedia();

            binding.playerView.setVisibility(View.VISIBLE);
            binding.defaultMariyam.setVisibility(View.INVISIBLE);
            initializePlayer(Uri.parse(video_url));
            }
        }
        else
         {  if(video_url!=null){
            Intent intent = new Intent(getActivity(), FullscreenActivity.class);
            intent.putExtra(KEY_VIDEO_URL_FULLSCREEN, video_url);
            startActivity(intent);}
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
                onStop();
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
                onStop();
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
    private void initializeMedia() {
        mediaSessionCompat = new MediaSessionCompat(getActivity(), TAG);
        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        playbackBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSessionCompat.setPlaybackState(playbackBuilder.build());
        mediaSessionCompat.setCallback(new SessionCallBacks());
        mediaSessionCompat.setActive(true);
    }
    @Override
    public void onPause() {
        //releasing in Pause and saving current position for resuming
        super.onPause();
        if (simpleExoPlayer != null) {
            positionPlayer = simpleExoPlayer.getCurrentPosition();
            //getting play when ready so that player can be properly store state on rotation
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (simpleExoPlayer != null) {
            //resuming properly
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(positionPlayer);
        } else {
            //Correctly initialize and play properly fromm seekTo function
            initializeMedia();
            initializePlayer(Uri.parse(video_url));
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(positionPlayer);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_READY && playWhenReady) {
            playbackBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        } else if (playbackState == ExoPlayer.STATE_READY) {
            playbackBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        }
        mediaSessionCompat.setPlaybackState(playbackBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }


    private class SessionCallBacks extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            super.onPlay();
            simpleExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            super.onPause();
            simpleExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            simpleExoPlayer.seekTo(0);
        }
    }


}
