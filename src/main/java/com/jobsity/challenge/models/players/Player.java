package com.jobsity.challenge.models.players;

import com.jobsity.challenge.models.frames.Frame;

import java.util.List;

public interface Player {

    /**
     * Gets the name of the player
     * @return
     */
    String getName();

    /**
     * Gets the frames played by the player
     * @return
     */
    List<Frame> getFrames();

    /**
     * Adds points to the player frames.
     * @param points
     */
    void setPoints(String points);

}
