package com.example.android.bakingapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.android.bakingapp.ViewManager.ExoPlayerViewManager;
import com.example.android.bakingapp.Fragment.StepsFragment;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.ActivityFullscreenBinding;
import com.google.android.exoplayer2.ui.PlayerView;

public class FullscreenActivity extends AppCompatActivity {

    private static final boolean AUTO_HIDE = true;
    ActivityFullscreenBinding binding;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private PlayerView mPlayerView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

            mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private String mVideoUrl;
    private ExoPlayerViewManager mExoPlayerViewManager;
    private boolean mDestroyVideo = true;
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreen);
        mVisible = true;

        mPlayerView = findViewById(R.id.full_player_view);
        mPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        ImageView fullScreenImageView = findViewById(R.id.exo_fullscreen_icon);
        fullScreenImageView.setImageResource(R.drawable.exo_controls_fullscreen_exit);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(StepsFragment.KEY_VIDEO_URL_FULLSCREEN)) {
            mVideoUrl = intent.getStringExtra(StepsFragment.KEY_VIDEO_URL_FULLSCREEN);
            Bundle onsavedstate = intent.getBundleExtra("saved_state");
        }
        mExoPlayerViewManager = ExoPlayerViewManager.getInstance(mVideoUrl, this);
        mExoPlayerViewManager.prepareExoPlayer(mPlayerView);
        mExoPlayerViewManager.goToForeground();

        FrameLayout fullScreenLayout = findViewById(R.id.exo_fullscreen_button);
        fullScreenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDestroyVideo = false;
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mDestroyVideo = false;
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDestroyVideo) {
            mExoPlayerViewManager.releaseVideoPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mExoPlayerViewManager.goToBackground();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}