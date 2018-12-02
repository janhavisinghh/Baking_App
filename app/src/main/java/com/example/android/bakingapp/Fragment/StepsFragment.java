package com.example.android.bakingapp.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Activity.FullscreenActivity;
import com.example.android.bakingapp.ExoPlayerViewManager;
import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static android.view.View.INVISIBLE;


public class StepsFragment extends Fragment {
    private String step_id;
    private String short_desc;
    private String description;
    private String video_url;
    private String thumbnail_url;
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
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private ImageView default_img;

    private int mResumeWindow;
    private long mResumePosition;

    private TextView step_desc;
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
        step_id = (String)getArguments().getSerializable("step_id");
        short_desc = (String)getArguments().getSerializable("short_desc");
        description = (String)getArguments().getSerializable("description");
        video_url = (String)getArguments().getSerializable("video_url");
        thumbnail_url = (String)getArguments().getSerializable("thumbnail_url");
        step_desc = (TextView) rootView.findViewById(R.id.step_desc_tv);
        default_img=(ImageView) rootView.findViewById(R.id.default_mariyam);
        mExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        mExoPlayerViewManager = ExoPlayerViewManager.getInstance(video_url, getContext());

        if(video_url.equals("")){
            mExoPlayerView.setVisibility(INVISIBLE);
            default_img.setVisibility(View.VISIBLE);

        }
        else{
            mExoPlayerView.setVisibility(View.VISIBLE);
            default_img.setVisibility(View.INVISIBLE);
        initializePlayer(Uri.parse(video_url));}

        FrameLayout fullscreenLayout = rootView.findViewById(R.id.exo_fullscreen_button);
        fullscreenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra(KEY_VIDEO_URL_FULLSCREEN, video_url);
                startActivity(intent);
            }
        });


        step_desc.setText(description);



        return rootView;
    }
    private void initializePlayer(Uri parse) {
        if(simpleExoPlayer== null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            mExoPlayerView.setPlayer(simpleExoPlayer);

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
