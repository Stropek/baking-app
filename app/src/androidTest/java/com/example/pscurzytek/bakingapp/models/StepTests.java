package com.example.pscurzytek.bakingapp.models;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class StepTests {

    @Test
    public void createFromParcel_returnsParceledStep() {
        // given
        Step step = new Step(1, "short desc", "desc", "video", "thumbnail");

        // when
        Parcel parcel = Parcel.obtain();
        step.writeToParcel(parcel, step.describeContents());
        parcel.setDataPosition(0);
        Step fromParcel = Step.CREATOR.createFromParcel(parcel);

        // then
        assertEquals(fromParcel.getId(), step.getId());
        assertEquals(fromParcel.getShortDescription(), step.getShortDescription());
        assertEquals(fromParcel.getDescription(), step.getDescription());
        assertEquals(fromParcel.getVideoUrl(), step.getVideoUrl());
        assertEquals(fromParcel.getThumbnailUrl(), step.getThumbnailUrl());
    }

    @Test
    public void newArray_returnsNewArrayOfGivenSize() {
        // when
        Step[] steps = Step.CREATOR.newArray(10);

        // then
        assertEquals(10, steps.length);
    }
}
