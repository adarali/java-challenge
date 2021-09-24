package com.jobsity.challenge.models.players;

import com.jobsity.challenge.models.frames.Frame;

import java.util.List;

public interface Player {
    String getName();
    List<Frame> getFrames();
    void setPoints(String points);

    static Player createPlayer(String name) {
        return new DefaultPlayer(name);
    }
}
