package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.misc.Constants;

public class FrameFactory {

    public static Frame createFrame(String points, int frameCount) {
        if (frameCount < Constants.FRAMES - 1) {
            return new RegularFrame(points, frameCount + 1);
        }
        return new FinalFrame(points, frameCount + 1);
    }
}
