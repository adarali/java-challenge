package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.misc.Constants;
import lombok.Getter;

import java.util.List;

@Getter
class FinalFrame extends AbstractFrame {
    public FinalFrame(String first, Integer frameNumber) {
        super(first, frameNumber);
    }

    @Override
    protected boolean setPoints(int points) {
        if (getAttempts().size() < 3) {
            getAttempts().add(points);
            return true;
        }
        return false;
    }

    @Override
    public boolean isStrike() {
        return false;
    }

    @Override
    public boolean isSpare() {
        return false;
    }

    @Override
    public boolean isDone() {
        int pins = Constants.PINS;
        List<Integer> attempts = getAttempts();
        if (!attempts.isEmpty()) {
            if(attempts.size() < 2) return false;
            int sum = attempts.get(0) + attempts.get(1);
            if(attempts.get(0) < pins && sum < pins) return true;
            return sum < pins || attempts.size() == 3;
        }
        return false;
    }
}
