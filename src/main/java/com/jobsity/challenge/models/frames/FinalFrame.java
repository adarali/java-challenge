package com.jobsity.challenge.models.frames;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Constants;
import lombok.Getter;

import java.util.List;

@Getter
class FinalFrame extends AbstractFrame {
    public FinalFrame(String points, Integer frameNumber) {
        super(points, frameNumber);
    }

    @Override
    protected boolean setPoints(int points) {
        if(isDone()) return false;
        int pins = Constants.PINS;
        List<Integer> attempts = getAttempts();
        if (attempts.size() < 3) {
            if (!attempts.isEmpty()) {
                int last = attempts.get(attempts.size() - 1);
                if ((attempts.size() == 1 && attempts.get(0) < 10 || attempts.size() == 2 && attempts.get(0) == 10 && attempts.get(1) < 10) && last < pins && last + points > pins) {
                    int left = pins - last;
                    if (left > 1) {
                        throw new AppException(String.format("There are only %s pins left", left));
                    } else {
                        throw new AppException(String.format("There is only %s pin left", left));
                    }
                }
            }
            attempts.add(points);
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
