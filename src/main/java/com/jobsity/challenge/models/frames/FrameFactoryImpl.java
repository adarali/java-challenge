package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.misc.Constants;
import com.jobsity.challenge.models.players.Player;
import org.springframework.stereotype.Component;

@Component
public class FrameFactoryImpl implements FrameFactory {

    @Override
    public Frame createFrame(Player player) {
        int frameCount = player.getFrames().size();
        if (frameCount < Constants.FRAMES - 1) {
            return new RegularFrame(frameCount + 1);
        }
        return new FinalFrame(frameCount + 1);
    }

}
