package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.misc.Constants;
import com.jobsity.challenge.models.players.Player;

public interface FrameFactory {
    Frame createFrame(Player player);
}
