package com.example.pscurzytek.bakingapp.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StepTests {

    @Test
    public void constructor_setsAllFields() {
        // when
        Step step= new Step(1, "short", "desc", "video", "thumbnail");

        // then
        assertEquals(1, step.getId());
        assertEquals("short", step.getShortDescription());
        assertEquals("desc", step.getDescription());
        assertEquals("video", step.getVideoUrl());
        assertEquals("thumbnail", step.getThumbnailUrl());
    }

    @Test
    public void setId_setsId() {
        // given
        Step step= new Step(1, "short", "desc", "video", "thumbnail");

        // when
        step.setId(10);

        // then
        assertEquals(10, step.getId());
    }

    @Test
    public void setShortDescription_setsShortDescription() {
        // given
        Step step= new Step(1, "short", "desc", "video", "thumbnail");

        // when
        step.setShortDescription("changed short");

        // then
        assertEquals("changed short", step.getShortDescription());
    }

    @Test
    public void setStep_setsStep() {
        // given
        Step step= new Step(1, "short", "desc", "video", "thumbnail");

        // when
        step.setDescription("changed desc");

        // then
        assertEquals("changed desc", step.getDescription());
    }

    @Test
    public void setVideoUrl_setsVideoUrl() {
        // given
        Step step= new Step(1, "short", "desc", "video", "thumbnail");

        // when
        step.setVideoUrl("changed video");

        // then
        assertEquals("changed video", step.getVideoUrl());
    }

    @Test
    public void setThumbnailUrl_setsThumbnailUrl() {
        // given
        Step step= new Step(1, "short", "desc", "video", "thumbnail");

        // when
        step.setThumbnailUrl("changed thumbnail");

        // then
        assertEquals("changed thumbnail", step.getThumbnailUrl());
    }
}
