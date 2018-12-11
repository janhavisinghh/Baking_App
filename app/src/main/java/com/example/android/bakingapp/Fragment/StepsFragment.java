package com.example.android.bakingapp.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.android.bakingapp.Activity.FullscreenActivity;
import com.example.android.bakingapp.Data.Steps;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.ViewManager.ExoPlayerViewManager;
import com.example.android.bakingapp.databinding.StepFragmentBinding;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;


public class StepsFragment extends Fragment {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_VIDEO_URL_FULLSCREEN = "video_url_fullscreen";
    public static final String MEDIA_POS = "MEDIA_POSITION";
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String KEY_POSITION = "position";
    public ExoPlayerViewManager mExoPlayerViewManager;
    long positionPlayer;
    StepFragmentBinding binding;
    boolean playWhenReady;
    private String description;
    private String video_url;
    private ArrayList<Steps> stepsList;
    private int position;
    private FrameLayout fullscreenLayout;
    private Boolean mTwopane;
    private SimpleExoPlayer simpleExoPlayer;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private Dialog mFullScreenDialog;
    private int mResumeWindow;
    private long mResumePosition;
    private MediaSessionCompat mediaSessionCompat;

    public StepsFragment() {
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

        final View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        stepsList = new ArrayList<>();
        binding = StepFragmentBinding.bind(rootView);
        description = (String) getArguments().getSerializable("description");
        video_url = (String) getArguments().getSerializable("video_url");
        stepsList = (ArrayList<Steps>) getArguments().getSerializable("stepsList");
        mTwopane = getArguments().getBoolean("mtwoPane");
        position = getArguments().getInt("position");
        if (savedInstanceState != null) {
            position = (Integer) savedInstanceState.getSerializable(KEY_POSITION);
            video_url = stepsList.get(position).getVideoURL();
        }


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


        if (savedInstanceState != null) {
            description = (String) savedInstanceState.getSerializable(KEY_DESCRIPTION);
        } else
            binding.stepDescTv.setText(description);

        binding.prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0)
                    position--;
                else
                    position = stepsList.size() - 1;

                binding.stepDescTv.setText(stepsList.get(position).getDescription());
                onDestroyView();
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
                onDestroyView();
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

    /**
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mExoPlayerViewManager != null && !(stepsList.get(position) == null || stepsList.get(position).getVideoURL().equals(""))) {
            if (!isVisibleToUser) {
                ExoPlayerViewManager.getInstance(video_url, getContext()).goToBackground();
            } else {
                ExoPlayerViewManager.getInstance(video_url, getContext()).prepareExoPlayer(binding.playerView);
                ExoPlayerViewManager.getInstance(video_url, getContext()).goToForeground();
            }
        }
    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        if (stepsList.get(position) != null && !stepsList.get(position).getVideoURL().equals("")) {
            ExoPlayerViewManager.getInstance(stepsList.get(position).getVideoURL(), getContext()).prepareExoPlayer(binding.playerView);
            if (getUserVisibleHint())
                ExoPlayerViewManager.getInstance(video_url, getContext()).goToForeground();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerViewManager.getInstance(video_url, getContext()).goToBackground();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!getActivity().isChangingConfigurations()) {
            ExoPlayerViewManager.getInstance(video_url, getContext()).releaseVideoPlayer();
        }
    }

    /**
     * @param videoView
     * @param videoUrl
     */
    private void setupPlayerView(final PlayerView videoView, final String videoUrl) {
        ExoPlayerViewManager.getInstance(videoUrl, getContext()).prepareExoPlayer(videoView);
        ExoPlayerViewManager.getInstance(videoUrl, getContext()).goToForeground();


        fullscreenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra(KEY_VIDEO_URL_FULLSCREEN, video_url);
                startActivity(intent);
            }
        });
    }

    /**
     * @param currentState
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putSerializable(KEY_DESCRIPTION, description);
        currentState.putSerializable(KEY_POSITION, position);
    }

}


