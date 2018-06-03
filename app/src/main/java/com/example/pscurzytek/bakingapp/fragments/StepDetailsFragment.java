package com.example.pscurzytek.bakingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.interfaces.OnStepSelectedListener;
import com.example.pscurzytek.bakingapp.models.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment
    implements Player.EventListener {

    private String TAG = StepDetailsFragment.class.getName();

    private Context context;
    private Step step;
    private SimpleExoPlayer player;
    private boolean startAutoPlay;
    private int startWindow;
    private long startPosition;

    private OnStepSelectedListener stepSelectedListener;

    @BindView(R.id.media_playerView) PlayerView mediaPlayerView;
    @BindView(R.id.step_instructions_textView) TextView instructionsTextView;
    @BindView(R.id.previous_button) Button previousButton;
    @BindView(R.id.next_button) Button nextButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        Bundle arguments = getArguments();
        if (arguments != null) {
            step = arguments.getParcelable(Constants.BundleKeys.StepDetails);
        }

        // TODO: if video url is valid, initialize player, otherwise if image is valid - display the image, otherwise display placeholder imageRe
        initializePlayer(context, step);

        if (savedInstanceState != null) {
            startAutoPlay = savedInstanceState.getBoolean(Constants.BundleKeys.PlayerAutoPlay);
            startWindow = savedInstanceState.getInt(Constants.BundleKeys.PlayerWindow);
            startPosition = savedInstanceState.getLong(Constants.BundleKeys.PlayerPosition);
        } else {
            clearStartPosition();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepSelectedListener = (OnStepSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        updateStartPosition();
        outState.putBoolean(Constants.BundleKeys.PlayerAutoPlay, startAutoPlay);
        outState.putInt(Constants.BundleKeys.PlayerWindow, startWindow);
        outState.putLong(Constants.BundleKeys.PlayerPosition, startPosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment, container, false);
        ButterKnife.bind(this, view);

        instructionsTextView.setText(step.getDescription());

        View decorView = getActivity().getWindow().getDecorView();
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mediaPlayerView.getLayoutParams();

        if (!stepSelectedListener.isBigScreen()
                && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.setMargins(0,0,0,0);
            instructionsTextView.setVisibility(View.GONE);
            previousButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else {
            int standardMargin = (int) getResources().getDimension(R.dimen.standard_margin);
            params.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
            params.setMargins(standardMargin, standardMargin, standardMargin, standardMargin);
            instructionsTextView.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.VISIBLE);
            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        }

        mediaPlayerView.setLayoutParams(params);
        mediaPlayerView.setPlayer(player);
        return view;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.d(TAG, String.format("PLAYER ERROR: %s", error.getMessage()));

        clearStartPosition();
        initializePlayer(context, step);
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

    private void initializePlayer(Context context, Step step) {
        if (player == null) {
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            player.addListener(this);

            String userAgent = Util.getUserAgent(context, getString(R.string.app_name));
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent, bandwidthMeter);

            Uri videoUri = Uri.parse(step.getVideoUrl());
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoUri);
            player.prepare(videoSource);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.INDEX_UNSET;
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }
}
