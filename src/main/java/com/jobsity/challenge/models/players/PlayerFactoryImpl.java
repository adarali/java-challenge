package com.jobsity.challenge.models.players;

import com.jobsity.challenge.models.frames.FrameFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerFactoryImpl implements PlayerFactory {

    private final FrameFactory frameFactory;

    @Override
    public Player createPlayer(String name) {
        return new DefaultPlayer(frameFactory, name);
    }
}
