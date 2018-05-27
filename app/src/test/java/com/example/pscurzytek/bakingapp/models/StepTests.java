package com.example.pscurzytek.bakingapp.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StepTests {

    @Test
    public void constructor_setsAllFields() {
        // when
        Step Step = new Step(1, "short", "desc", "video", "thumbnail");

        // then
        assertEquals(1, Step.getId());
        assertEquals("short", Step.getShortDescription());
        assertEquals("desc", Step.getDescription());
        assertEquals("video", Step.getVideoUrl());
        assertEquals("thumbnail", Step.getThumbnailUrl());
    }

    @Test
    public void setId_setsId() {
        // given
        Step Step = new Step(1, "short", "desc", "video", "thumbnail");

        // when
        Step.setId(10);

        // then
        assertEquals(10, Step.getId());
    }

    @Test
    public void setShortDescription_setsShortDescription() {
        // given
        Step Step = new Step(1, "short", "desc", "video", "thumbnail");

        // when
        Step.setShortDescription("changed short");

        // then
        assertEquals("changed short", Step.getShortDescription());
    }

    @Test
    public void setStep_setsStep() {
        // given
        Step Step = new Step(1, "short", "desc", "video", "thumbnail");

        // when
        Step.setDescription("changed desc");

        // then
        assertEquals("changed desc", Step.getDescription());
    }

    @Test
    public void setVideoUrl_setsVideoUrl() {
        // given
        Step Step = new Step(1, "short", "desc", "video", "thumbnail");

        // when
        Step.setVideoUrl("changed video");

        // then
        assertEquals("changed video", Step.getVideoUrl());
    }

    @Test
    public void setThumbnailUrl_setsThumbnailUrl() {
        // given
        Step Step = new Step(1, "short", "desc", "video", "thumbnail");

        // when
        Step.setThumbnailUrl("changed thumbnail");

        // then
        assertEquals("changed thumbnail", Step.getThumbnailUrl());
    }
}
