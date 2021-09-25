package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.misc.Constants;

public class FrameFactory {

    public static Frame createFrame(int frameCount) {
        if (frameCount < Constants.FRAMES - 1) {
            return new RegularFrame(frameCount + 1);
        }
        return new FinalFrame(frameCount + 1);
    }
}
