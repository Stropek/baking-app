package com.example.pscurzytek.bakingapp.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.pscurzytek.bakingapp.Constants;
import com.example.pscurzytek.bakingapp.R;
import com.example.pscurzytek.bakingapp.models.Step;
import com.example.pscurzytek.bakingapp.utils.ObjectFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class StepDetailsActivityTests {

    @Rule
    public ActivityTestRule<StepDetailsActivity> testRule =
            new ActivityTestRule<>(StepDetailsActivity.class, false, false);

    @Test
    public void default_displaysStepDetails() {
        // given
        Step step = new Step(1, "test step", "test step description", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", "thumbnail");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.StepDetails, step);

        // when
        testRule.launchActivity(intent);

        // then
        onView(withId(R.id.media_playerView)).check(matches(isDisplayed()));
        onView(withId(R.id.thumbnail_imageView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.step_instructions_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.step_instructions_textView)).check(matches(withText("test step description")));
        onView(withId(R.id.previous_button)).check(matches(isDisplayed()));
        onView(withId(R.id.next_button)).check(matches(isDisplayed()));
    }

    @Test
    public void toggleOrientation_contentTakesFullScreen() {
        // given
        Step step = new Step(1, "test step", "test step description", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", "thumbnail");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.StepDetails, step);
        testRule.launchActivity(intent);

        // when
        testRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // then
        onView(withId(R.id.media_playerView)).check(matches(isDisplayed()));
        onView(withId(R.id.step_instructions_textView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void toggleOrientationTwice_contentTakesFullScreen() {
        // given
        Step step = new Step(1, "test step", "test step description", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4", "thumbnail");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.StepDetails, step);
        testRule.launchActivity(intent);

        // when
        testRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        testRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // then
        onView(withId(R.id.media_playerView)).check(matches(isDisplayed()));
        onView(withId(R.id.step_instructions_textView)).check(matches(not(isDisplayed())));
    }

    @Test
    public void noVideoButValidThumbnailUrl_displaysThumbnailImageView() {
        // given
        Step step = new Step(1, "test step", "test step description", "", "https://kconcrete.com/wp-content/uploads/2017/08/product-video-placeholder.jpg");

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.StepDetails, step);

        // when
        testRule.launchActivity(intent);

        // then
        onView(withId(R.id.media_playerView)).check(matches(not(isDisplayed())));
        onView(withId(R.id.thumbnail_imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.step_instructions_textView)).check(matches(isDisplayed()));
        onView(withId(R.id.step_instructions_textView)).check(matches(withText("test step description")));
        onView(withId(R.id.previous_button)).check(matches(isDisplayed()));
        onView(withId(R.id.next_button)).check(matches(isDisplayed()));    }

    @Test
    public void navigateThroughSteps_Next_navigatesBackToFirstStep() {
        // given
        ArrayList<Step> steps = ObjectFactory.createSteps(3);
        Step step = steps.get(0);

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.StepDetails, step);
        intent.putExtra(Constants.BundleKeys.StepsList, steps);

        testRule.launchActivity(intent);

        // when
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.next_button)).perform(click());
        onView(withId(R.id.next_button)).perform(click());

        // then
        onView(withId(R.id.step_instructions_textView)).check(matches(withText("desc 0")));
    }

    @Test
    public void navigateThroughSteps_Previous_navigatesBackToFirstStep() {
        // given
        ArrayList<Step> steps = ObjectFactory.createSteps(3);
        Step step = steps.get(steps.size() - 1);

        Intent intent = new Intent();
        intent.putExtra(Constants.BundleKeys.StepDetails, step);
        intent.putExtra(Constants.BundleKeys.StepsList, steps);

        testRule.launchActivity(intent);

        // when
        onView(withId(R.id.previous_button)).perform(click());
        onView(withId(R.id.previous_button)).perform(click());
        onView(withId(R.id.previous_button)).perform(click());

        // then
        onView(withId(R.id.step_instructions_textView)).check(matches(withText("desc 2")));
    }
}