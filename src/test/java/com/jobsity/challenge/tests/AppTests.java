package com.jobsity.challenge.tests;


import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.models.frames.Frame;
import com.jobsity.challenge.models.frames.FrameFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTests {

    @Test
    void testStrike() {
        Frame frame = FrameFactory.createFrame("10", 0);
        assertTrue(frame.isStrike());
    }

    @Test
    void testSpare() {
        Frame frame = FrameFactory.createFrame("6", 1);
        frame.setPoints("4");
        assertTrue(frame.isSpare());
    }

    @Test
    void testInvalidPoints() {
        assertThrows(AppException.class, () -> {
            Frame frame = FrameFactory.createFrame("6", 1);
            frame.setPoints("5");
            frame.setPoints("6");
        });
    }


}
