package com.jobsity.challenge.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.jobsity.challenge.misc.Constants.*;

@Getter @Setter
@EqualsAndHashCode(of = "name")
public class Player {
    private final String name;
    private final List<Frame> frames = new ArrayList<>();
    private int score = 0;
    private int attempts = 0;
    private int acc = 0;
    private Map<Integer, Frame> strikes = new HashMap<>();
    private Map<Integer, Frame> spares = new HashMap<>();

    public Player(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        attempts += 1;
        if (frames.isEmpty()) {
            addFrame(points);
        } else {
            Frame current = getCurrentFrame();
            if (current.setPoints(points)) {
                if (current.isSpare()) {
                    spares.put(attempts, current);
                }
            } else {
                addFrame(points);
            }
        }
        if (strikes.containsKey(attempts - 1)) {
            strikes.get(attempts - 1).addExtraPoints(points);
        }

        if (strikes.containsKey(attempts - 2)) {
            Frame strike = strikes.remove(attempts - 2);
            strike.addExtraPoints(points);
            setFrameScore(strike);
        }
        if (spares.containsKey(attempts - 1)) {
            Frame spare = spares.remove(attempts - 1);
            spare.addExtraPoints(points);
            setFrameScore(spare);
        }

        if (getCurrentFrame().isNormal()) {
            Frame frame = getCurrentFrame();
            setFrameScore(frame);
        }
    }

    private Frame getCurrentFrame() {
        if(frames.isEmpty()) return null;
        return frames.get(frames.size() - 1);
    }

    private void setFrameScore(Frame frame) {
        this.score = frame.getTotalPoints() + this.score;
        frame.setScore(this.score);
    }

    private void addFrame(int points) {
        Frame frame;
        if (frames.size() < FRAMES - 1) {
            frame = new Frame(points, frames.size() + 1);
        } else {
            frame = new FinalFrame(points, frames.size() + 1);
        }
        frames.add(frame);
        if (frame.isStrike()) {
            strikes.put(attempts, frame);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("name='").append(name).append('\'');
        sb.append(", frames=").append(frames);
        sb.append(", score=").append(score);
        sb.append('}');
        return sb.toString();
    }
}