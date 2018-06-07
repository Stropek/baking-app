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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.activities.RecipeDetailsActivity;
import com.example.pscurzytek.bakingapp.interfaces.OnStepNavigationListener;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class StepDetailsFragment extends Fragment
    implements Player.EventListener {

    private String TAG = StepDetailsFragment.class.getName();

    private Context context;
    private Step step;
    private SimpleExoPlayer player;
    private boolean isBigScreen = false;

    private boolean startAutoPlay;
    private int startWindow;
    private long startPosition;

    private OnStepNavigationListener stepNavigationListener;

    @BindView(R.id.content) LinearLayout contentLinearLayout;
    @BindView(R.id.media_playerView) PlayerView mediaPlayerView;
    @BindView(R.id.thumbnail_imageView) ImageView thumbnailImageView;
    @BindView(R.id.step_instructions_textView) TextView instructionsTextView;
    @BindView(R.id.previous_button) @Nullable Button previousButton;
    @BindView(R.id.next_button) @Nullable Button nextButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        Bundle arguments = getArguments();

        if (savedInstanceState == null) {
            if (arguments != null) {
                step = arguments.getParcelable(Constants.BundleKeys.StepDetails);
            }
            clearStartPosition();
        } else {
            step = savedInstanceState.getParcelable(Constants.BundleKeys.StepDetails);
            startAutoPlay = savedInstanceState.getBoolean(Constants.BundleKeys.PlayerAutoPlay);
            startWindow = savedInstanceState.getInt(Constants.BundleKeys.PlayerWindow);
            startPosition = savedInstanceState.getLong(Constants.BundleKeys.PlayerPosition);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context.getClass().getName().equals(RecipeDetailsActivity.class.getName())) {
                OnStepSelectedListener stepSelectedListener = (OnStepSelectedListener) context;
                isBigScreen = stepSelectedListener.isBigScreen();
            }
            stepNavigationListener = (OnStepNavigationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        updateStartPosition();
        outState.putParcelable(Constants.BundleKeys.StepDetails, step);
        outState.putBoolean(Constants.BundleKeys.PlayerAutoPlay, startAutoPlay);
        outState.putInt(Constants.BundleKeys.PlayerWindow, startWindow);
        outState.putLong(Constants.BundleKeys.PlayerPosition, startPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details_fragment, container, false);
        ButterKnife.bind(this, view);

        initializePlayer(context, step);
        instructionsTextView.setText(step.getDescription());

        boolean showThumbnail = step.getVideoUrl().isEmpty() && !step.getThumbnailUrl().isEmpty();

        View decorView = getActivity().getWindow().getDecorView();

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) contentLinearLayout.getLayoutParams();

        if (!isBigScreen && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.setMargins(0,0,0,0);
            instructionsTextView.setVisibility(View.GONE);
            ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else {
            int standardMargin = (int) getResources().getDimension(R.dimen.standard_margin);
            params.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
            params.setMargins(standardMargin, standardMargin, standardMargin, standardMargin);
            instructionsTextView.setVisibility(View.VISIBLE);
            ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        }

        if (showThumbnail) {
            mediaPlayerView.setVisibility(View.GONE);
            thumbnailImageView.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(step.getThumbnailUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(thumbnailImageView);
        } else {
            thumbnailImageView.setVisibility(View.GONE);
            mediaPlayerView.setVisibility(View.VISIBLE);
            mediaPlayerView.setPlayer(player);
        }

        contentLinearLayout.setLayoutParams(params);

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

    @OnClick(R.id.previous_button) @Optional
    public void onPreviousButtonClicked() {
        stepNavigationListener.navigateToStep(step.getId() - 1);
    }

    @OnClick(R.id.next_button) @Optional
    public void onNextButtonClicked() {
        stepNavigationListener.navigateToStep(step.getId() + 1);
    }

    private void initializePlayer(Context context, Step step) {

        if (player == null) {
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            player.addListener(this);
            player.setPlayWhenReady(startAutoPlay);

            String userAgent = Util.getUserAgent(context, getString(R.string.app_name));
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent, bandwidthMeter);

            Uri videoUri = Uri.parse(step.getVideoUrl());
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoUri);

            boolean haveStartPosition = startWindow != C.INDEX_UNSET;
            if (haveStartPosition) {
                player.seekTo(startWindow, startPosition);
            }
            player.prepare(videoSource, !haveStartPosition, false);
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
